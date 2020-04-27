package com.octo.bankoperations.mapper;


import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDTO;

public class VirementMapper {

    public static VirementDTO map(Virement virement) {
        if (virement == null) return null;
        VirementDTO virementDto = new VirementDTO();
        virementDto.setId(virement.getId());
        virementDto.setAmount(virement.getMontantVirement());
        virementDto.setRibBeneficiaire(virement.getRibBeneficiaire());
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
        bankTransferDTO.setReceiverRIB(virement.getRibBeneficiaire());
        bankTransferDTO.setSenderRIB(virement.getCompteEmetteur().getRib());
        return bankTransferDTO;
    }
}
