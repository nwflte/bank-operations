package com.octo.bankoperations.service;

import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ObligationService {

    List<CordaDDRObligationDTO> loadAll(StateStatus stateStatus);

    Optional<CordaDDRObligationDTO> findById(String id);

    Optional<String> createPledge(long amount);

    Optional<String> cancelPledge(String externalId);

    Optional<String> denyPledge(String externalId);

    Optional<String> approvePledge(String externalId);

    Optional<String> createRedeem(long amount);

    Optional<String> cancelRedeem(String externalId);

    Optional<String> denyRedeem(String externalId);

    Optional<String> approveRedeem(String externalId);

}
