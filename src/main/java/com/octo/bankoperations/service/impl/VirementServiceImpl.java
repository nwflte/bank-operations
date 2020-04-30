package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.amqp.AMQPSender;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDTO;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.exceptions.CompteNonExistantException;
import com.octo.bankoperations.exceptions.SoldeDisponibleInsuffisantException;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.repository.VirementRepository;
import com.octo.bankoperations.service.CompteService;
import com.octo.bankoperations.service.VirementService;
import com.octo.bankoperations.utils.AccountUtils;
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

    private final CompteRepository compteRepository;
    private final CompteService compteService;
    private final VirementRepository virementRepository;
    private final InterBankTransferServiceVaultImpl interBankTransferServiceVault;
    private final IntraBankTransferServiceVaultImpl intraBankTransferServiceVault;
    private final AMQPSender amqpSender;

    @Autowired
    public VirementServiceImpl(CompteRepository compteRepository, CompteService compteService, VirementRepository virementRepository,
                               InterBankTransferServiceVaultImpl interBankTransferServiceVault, IntraBankTransferServiceVaultImpl intraBankTransferServiceVault, AMQPSender sender) {
        this.compteRepository = compteRepository;
        this.compteService = compteService;
        this.virementRepository = virementRepository;
        this.interBankTransferServiceVault = interBankTransferServiceVault;
        this.intraBankTransferServiceVault = intraBankTransferServiceVault;
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
    public List<Virement> findAllForUtilisateur(Long utilisateurId) {
        Compte compte = compteService.getForUtilisateur(utilisateurId).orElseThrow(CompteNonExistantException::new);
        return virementRepository.findAllByRibEmetteurOrRibBeneficiaire(compte.getRib(), compte.getRib());
    }

    @Override
    public void virement(VirementDTO virementDto) {
        Compte compteEmetteur = compteRepository.findByRib(virementDto.getRibEmetteur())
                .orElseThrow(() -> new CompteNonExistantException(virementDto.getRibEmetteur()));

        if (compteEmetteur.getSolde().compareTo(virementDto.getAmount()) < 0) {
            throw new SoldeDisponibleInsuffisantException(
                    "Solde insuffisant pour l'utilisateur " + compteEmetteur.getUtilisateur().getUsername());
        }

        Virement virement = new Virement();
        virement.setReference(VirementUtils.generateReference());
        virement.setDateExecution(virementDto.getDate());
        virement.setMontantVirement(virementDto.getAmount());
        virement.setMotifVirement(virementDto.getMotif());
        virement.setRibBeneficiaire(virementDto.getRibBeneficiaire());
        virement.setRibEmetteur(virementDto.getRibEmetteur());

        if(isVirementInterne(virementDto)){
            Compte compteBeneficiaire = compteRepository.findByRib(virementDto.getRibBeneficiaire())
                    .orElseThrow(() -> new CompteNonExistantException(virementDto.getRibBeneficiaire()));

            compteBeneficiaire.setSolde(compteBeneficiaire.getSolde().add(virementDto.getAmount()));
            compteRepository.save(compteBeneficiaire);
            compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virementDto.getAmount()));
            compteRepository.save(compteEmetteur);
            virement.setStatus(VirementStatus.INTERNE_NOT_SAVED_IN_CORDA);
        }
        else virement.setStatus(VirementStatus.EXTERNE_PENDING_APPROVAL);

        virementRepository.save(virement);
        BankTransferDTO bankTransferDTO = VirementMapper.mapToBankTransferDTO(virement);
        amqpSender.send(bankTransferDTO);
    }

    /**
     * Executed after consuming message from queue
     * @param reference
     */
    @Override
    public void virementInterneSavedToBlockchain(String reference) {
        Virement virement = virementRepository.findByReference(reference).orElseThrow(EntityNotFoundException::new);
        virement.setStatus(VirementStatus.INTERNE_SAVED_IN_CORDA);
        virement.setDateUpdateStatus(new Date());
        virementRepository.save(virement);
    }

    @Override
    public void virementReceivedFromBlockchain(BankTransferDTO dto) {
        Virement virement = new Virement();
        virement.setReference(dto.getReference());
        virement.setStatus(dto.getStatus());
        virement.setRibEmetteur(dto.getSenderRIB());
        virement.setRibBeneficiaire(dto.getReceiverRIB());
        virement.setMontantVirement(dto.getAmount());
        virement.setDateUpdateStatus(new Date());

        virementRepository.save(virement);
    }

    private boolean isVirementInterne(VirementDTO virementDto) {
        String codeBankEmetteur = AccountUtils.getBankCode(virementDto.getRibEmetteur());
        String codeBankBeneficiaire = AccountUtils.getBankCode(virementDto.getRibBeneficiaire());
        return AccountUtils.getBankCode(codeBankEmetteur).equals(AccountUtils.getBankCode(codeBankBeneficiaire));
    }

}