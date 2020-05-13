package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDTO;

import java.util.List;
import java.util.Optional;

public interface VirementService {
    List<Virement> loadAll();

    Optional<Virement> findById(Long id);

    List<Virement> findAllForClient(Long utilisateurId);

    Virement virement(VirementDTO virementDto);

    Virement saveVirementAddedToBlockchain(String reference);

    Virement saveVirementReceivedFromBlockchain(BankTransferDTO dto);
}
