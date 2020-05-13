package com.octo.bankoperations.mapper;


import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDTO;

public class VirementMapper {

    public static VirementDTO map(Virement virement) {
        if (virement == null) return null;
        VirementDTO virementDto = new VirementDTO();
        virementDto.setReference(virement.getReference());
        virementDto.setId(virement.getId());
        virementDto.setAmount(virement.getAmount());
        virementDto.setRibBeneficiaire(virement.getRibBeneficiaire());
        virementDto.setRibEmetteur(virement.getRibEmetteur());
        virementDto.setDateExecution(virement.getDateExecution());
        virementDto.setMotif(virement.getMotif());
        virementDto.setStatus(virement.getStatus());
        virementDto.setDateUpdateStatus(virement.getDateUpdateStatus());
        return virementDto;

    }

    public static BankTransferDTO mapToBankTransferDTO(Virement virement) {
        if(virement == null) return null;
        BankTransferDTO bankTransferDTO = new BankTransferDTO();
        bankTransferDTO.setReference(virement.getReference());
        bankTransferDTO.setAmount(virement.getAmount());
        bankTransferDTO.setExecutionDate(virement.getDateExecution());
        bankTransferDTO.setReceiverRIB(virement.getRibBeneficiaire());
        bankTransferDTO.setSenderRIB(virement.getRibEmetteur());
        bankTransferDTO.setStatus(virement.getStatus());
        bankTransferDTO.setStatusUpdate(virement.getDateUpdateStatus());
        return bankTransferDTO;
    }

    public static Virement mapToVirement(BankTransferDTO dto){
        Virement virement = new Virement();
        virement.setStatus(dto.getStatus());
        virement.setDateExecution(dto.getExecutionDate());
        virement.setRibEmetteur(dto.getSenderRIB());
        virement.setRibBeneficiaire(dto.getReceiverRIB());
        virement.setAmount(dto.getAmount());
        virement.setReference(dto.getReference());
        virement.setDateUpdateStatus(dto.getStatusUpdate());
        return virement;
    }
}
