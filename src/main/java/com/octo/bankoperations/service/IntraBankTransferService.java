package com.octo.bankoperations.service;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaIntraBankTransferDTO;

import java.util.List;
import java.util.Optional;

public interface IntraBankTransferService {

    List<CordaIntraBankTransferDTO> loadAll();

    Optional<CordaIntraBankTransferDTO> findById(String id);

    Optional<String> transfer(BankTransferDTO dto);

}
