package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.amqp.AMQPSender;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDTO;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.exceptions.CompteNonExistantException;
import com.octo.bankoperations.exceptions.IllegalReceivedVirementStatusException;
import com.octo.bankoperations.exceptions.SoldeDisponibleInsuffisantException;
import com.octo.bankoperations.mapper.CompteMapper;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.repository.VirementRepository;
import com.octo.bankoperations.service.CompteService;
import com.octo.bankoperations.service.VirementService;
import com.octo.bankoperations.utils.VirementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VirementServiceImpl implements VirementService {

    private final CompteService compteService;
    private final VirementRepository virementRepository;
    private final AMQPSender amqpSender;

    @Autowired
    public VirementServiceImpl(CompteService compteService,
                               VirementRepository virementRepository, AMQPSender sender) {
        this.compteService = compteService;
        this.virementRepository = virementRepository;
        this.amqpSender = sender;
    }

    @Override
    public List<Virement> loadAll() {
        return virementRepository.findAll();
    }

    @Override
    public Optional<Virement> findById(Long id) {
        return virementRepository.findById(id);
    }

    @Override
    public List<Virement> findAllForClient(Long clientId) {
        Compte compte = compteService.getComptesForClient(clientId).orElseThrow(CompteNonExistantException::new);
        return virementRepository.findVirementsByRibEmetteurOrRibBeneficiaire(compte.getRib(), compte.getRib());
    }

    @Override
    public Virement virement(VirementDTO virementDto) {
        Compte compteEmetteur = compteService.findByRib(virementDto.getRibEmetteur())
                .orElseThrow(() -> new CompteNonExistantException(virementDto.getRibEmetteur()));

        if (compteEmetteur.getSolde().compareTo(virementDto.getAmount()) < 0) {
            throw new SoldeDisponibleInsuffisantException(
                    "Solde insuffisant pour le cllient " + compteEmetteur.getClient().getUsername());
        }

        Virement virement = new Virement();
        virement.setReference(VirementUtils.generateReference());
        virement.setDateExecution(virementDto.getDateExecution());
        virement.setAmount(virementDto.getAmount());
        virement.setMotif(virementDto.getMotif());
        virement.setRibBeneficiaire(virementDto.getRibBeneficiaire());
        virement.setRibEmetteur(virementDto.getRibEmetteur());
        virement.setDateUpdateStatus(new Date());

        if (VirementUtils.isVirementInterne(virementDto)) {
            Compte compteBeneficiaire = compteService.findByRib(virementDto.getRibBeneficiaire())
                    .orElseThrow(() -> new CompteNonExistantException(virementDto.getRibBeneficiaire()));

            compteBeneficiaire.setSolde(compteBeneficiaire.getSolde().add(virementDto.getAmount()));
            compteService.save(compteBeneficiaire);
            compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virementDto.getAmount()));
            compteService.save(compteEmetteur);
            virement.setStatus(VirementStatus.INTERNE_PENDING_SAVE_IN_CORDA);
        } else {
            virement.setStatus(VirementStatus.EXTERNE_PENDING_APPROVAL);
        }

        virementRepository.save(virement);
        BankTransferDTO bankTransferDTO = VirementMapper.mapToBankTransferDTO(virement);
        amqpSender.send(bankTransferDTO);
        return virement;
    }

    /**
     * Change status of transfer initiated by our bank when successfully sent processed in Blockchain.
     */
    @Override
    public Virement saveVirementAddedToBlockchain(String reference) {
        Virement virement = virementRepository.findByReference(reference).orElseThrow(EntityNotFoundException::new);
        if (VirementUtils.isVirementInterne(VirementMapper.map(virement))) {
            virement.setStatus(VirementStatus.INTERNE_SAVED_IN_CORDA);
        } else {
            virement.setStatus(VirementStatus.EXTERNE_APPROVED);
        }
        virement.setDateUpdateStatus(new Date());
        virementRepository.save(virement);
        return virement;
    }

    /**
     * Save a transfer initiated by another bank to our database. We receive the transfer details after it was
     * processed by node.
     */
    @Override
    public Virement saveVirementReceivedFromBlockchain(BankTransferDTO dto) {
        if (!dto.getStatus().equals(VirementStatus.EXTERNE_REJECTED) &&
                !dto.getStatus().equals(VirementStatus.EXTERNE_APPROVED))
            throw new IllegalReceivedVirementStatusException(dto.getStatus());
        Virement virement = VirementMapper.mapToVirement(dto);
        virement.setDateUpdateStatus(new Date());
        virementRepository.save(virement);
        return virement;
    }

}