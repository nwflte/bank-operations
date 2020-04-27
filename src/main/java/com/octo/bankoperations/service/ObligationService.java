package com.octo.bankoperations.service;

import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.enums.StateStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ObligationService {

    List<CordaDDRObligationDTO> loadAllObligations(StateStatus stateStatus);

    List<CordaDDRObligationDTO> loadAllPledges(StateStatus stateStatus);

    List<CordaDDRObligationDTO> loadAllRedeems(StateStatus stateStatus);

    Optional<CordaDDRObligationDTO> findObligationById(String id);

    Optional<CordaDDRObligationDTO> findPledgeById(String id);

    Optional<CordaDDRObligationDTO> findRedeemById(String id);

    Optional<String> createPledge(long amount);

    Optional<String> cancelPledge(String externalId);

    Optional<String> denyPledge(String externalId);

    Optional<String> approvePledge(String externalId);

    Optional<String> createRedeem(long amount);

    Optional<String> cancelRedeem(String externalId);

    Optional<String> denyRedeem(String externalId);

    Optional<String> approveRedeem(String externalId);

}
