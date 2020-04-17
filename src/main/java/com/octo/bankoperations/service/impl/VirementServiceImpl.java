package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.domain.VirementExterne;
import com.octo.bankoperations.domain.VirementInterne;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDto;
import com.octo.bankoperations.exceptions.CompteNonExistantException;
import com.octo.bankoperations.exceptions.SoldeDisponibleInsuffisantException;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.repository.VirementRepository;
import com.octo.bankoperations.service.VirementService;
import com.octo.bankoperations.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VirementServiceImpl implements VirementService {

    private final CompteRepository compteRepository;
    private final VirementRepository virementRepository;
    private final InterBankTransferServiceVaultImpl interBankTransferServiceVault;
    private final IntraBankTransferServiceVaultImpl intraBankTransferServiceVault;

    @Autowired
    public VirementServiceImpl(CompteRepository compteRepository, VirementRepository virementRepository,
                               InterBankTransferServiceVaultImpl interBankTransferServiceVault, IntraBankTransferServiceVaultImpl intraBankTransferServiceVault) {
        this.compteRepository = compteRepository;
        this.virementRepository = virementRepository;
        this.interBankTransferServiceVault = interBankTransferServiceVault;
        this.intraBankTransferServiceVault = intraBankTransferServiceVault;
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
    public void virement(VirementDto virementDto) {
        if (isVirementInterne(virementDto))
            virementInterne(virementDto);
        else
            virementExterne(virementDto);
    }


    private boolean isVirementInterne(VirementDto virementDto) {
        String codeBankEmetteur = AccountUtils.getBankCode(virementDto.getRibEmetteur());
        String codeBankBeneficiaire = AccountUtils.getBankCode(virementDto.getRibBeneficiaire());
        return AccountUtils.getBankCode(codeBankEmetteur).equals(AccountUtils.getBankCode(codeBankBeneficiaire));
    }

    private void virementInterne(VirementDto virementDto) {
        Compte compteEmetteur = compteRepository.findByRib(virementDto.getRibEmetteur())
                .orElseThrow(() -> new CompteNonExistantException("Compte non existant"));
        Compte compteBeneficiaire = compteRepository.findByRib(virementDto.getRibBeneficiaire())
                .orElseThrow(() -> new CompteNonExistantException("Compte non existant"));

        if (compteEmetteur.getSolde().compareTo(virementDto.getMontantVirement()) < 0) {
            throw new SoldeDisponibleInsuffisantException(
                    "Solde insuffisant pour l'utilisateur " + compteEmetteur.getUtilisateur().getUsername());
        }

        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virementDto.getMontantVirement()));
        compteRepository.save(compteEmetteur);

        compteBeneficiaire.setSolde(compteBeneficiaire.getSolde().add(virementDto.getMontantVirement()));
        compteRepository.save(compteBeneficiaire);

        VirementInterne virement = new VirementInterne();
        virement.setDateExecution(virementDto.getDate());
        virement.setCompteBeneficiaire(compteBeneficiaire);
        virement.setCompteEmetteur(compteEmetteur);
        virement.setMontantVirement(virementDto.getMontantVirement());

        virementRepository.save(virement);
        BankTransferDTO bankTransferDTO = VirementMapper.mapToBankTransferDTO(virement);
        intraBankTransferServiceVault.transfer(bankTransferDTO);
    }

    private void virementExterne(VirementDto virementDto) {

        Compte compteEmetteur = compteRepository.findByRib(virementDto.getRibEmetteur())
                .orElseThrow(() -> new CompteNonExistantException("Compte non existant"));

        if (compteEmetteur.getSolde().compareTo(virementDto.getMontantVirement()) < 0) {
            throw new SoldeDisponibleInsuffisantException(
                    "Solde insuffisant pour l'utilisateur " + compteEmetteur.getUtilisateur().getUsername());
        }

        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(virementDto.getMontantVirement()));
        compteRepository.save(compteEmetteur);

        VirementExterne virement = new VirementExterne();
        virement.setDateExecution(virementDto.getDate());
        virement.setNrCompteBeneficiaire(virementDto.getRibBeneficiaire());
        virement.setCompteEmetteur(compteEmetteur);
        virement.setMontantVirement(virementDto.getMontantVirement());
        virement.setValidated(false);

        virementRepository.save(virement);
        BankTransferDTO bankTransferDTO = VirementMapper.mapToBankTransferDTO(virement);
        interBankTransferServiceVault.transfer(bankTransferDTO);
    }

}