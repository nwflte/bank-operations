package com.octo.bankoperations.service;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaInterBankTransferDTO;

import java.util.List;
import java.util.Optional;

public interface InterBankTransferService {

    List<CordaInterBankTransferDTO> loadAll();

    Optional<CordaInterBankTransferDTO> findById(String id);

    Optional<String> transfer(BankTransferDTO dto);

}
