package com.octo.bankoperations.mapper;


import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.domain.VirementExterne;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDto;

public class VirementMapper {

    public static VirementDto map(Virement virement) {
        if (virement == null) return null;
        VirementDto virementDto = new VirementDto();
        virementDto.setMontantVirement(virement.getMontantVirement());
        virementDto.setRibBeneficiaire(virement.getNrCompteBeneficiaire());
        virementDto.setRibEmetteur(virement.getCompteEmetteur().getRib());
        virementDto.setDate(virement.getDateExecution());
        virementDto.setMotif(virement.getMotifVirement());

        return virementDto;

    }

    public static BankTransferDTO mapToBankTransferDTO(Virement virement) {
        if(virement == null) return null;
        BankTransferDTO bankTransferDTO = new BankTransferDTO();
        bankTransferDTO.setAmount(virement.getMontantVirement());
        bankTransferDTO.setExecutionDate(virement.getDateExecution());
        bankTransferDTO.setReceiverRIB(virement.getNrCompteBeneficiaire());
        bankTransferDTO.setSenderRIB(virement.getCompteEmetteur().getRib());
        return bankTransferDTO;
    }
}
