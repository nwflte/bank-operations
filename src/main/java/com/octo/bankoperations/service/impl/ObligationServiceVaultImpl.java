package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.dto.ObligationRequestDTO;
import com.octo.bankoperations.dto.ObligationUpdateDTO;
import com.octo.bankoperations.enums.DDRObligationType;
import com.octo.bankoperations.enums.StateStatus;
import com.octo.bankoperations.service.ObligationService;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ObligationServiceVaultImpl implements ObligationService {

    private final String obligationsURL;
    private final KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    public ObligationServiceVaultImpl(KeycloakRestTemplate keycloakRestTemplate, Constants constants) {
        this.keycloakRestTemplate = keycloakRestTemplate;
        obligationsURL = constants.getNodeUrl() + "/api/obligations/";
    }

    @Override
    public List<CordaDDRObligationDTO> loadAllObligations(StateStatus stateStatus) {
        ResponseEntity<List<CordaDDRObligationDTO>> response = keycloakRestTemplate.exchange(obligationsURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaDDRObligationDTO>>() {
                });
        return response.getBody();
    }

    @Override
    public List<CordaDDRObligationDTO> loadAllPledges(StateStatus stateStatus) {
        return loadAllObligations(stateStatus).stream().filter(ob -> ob.getType().equals(DDRObligationType.PLEDGE))
                .collect(Collectors.toList());
    }

    @Override
    public List<CordaDDRObligationDTO> loadAllRedeems(StateStatus stateStatus) {
        return loadAllObligations(stateStatus).stream().filter(ob -> ob.getType().equals(DDRObligationType.REDEEM))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CordaDDRObligationDTO> findObligationByExternalId(String externalId) {
        String url = obligationsURL + externalId;
        ResponseEntity<CordaDDRObligationDTO> response = keycloakRestTemplate.getForEntity(url, CordaDDRObligationDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<CordaDDRObligationDTO> findPledgeByExternalId(String id) {
        Optional<CordaDDRObligationDTO> obligation = findObligationByExternalId(id);
        return obligation.isPresent() && obligation.get().getType().equals(DDRObligationType.PLEDGE) ?
                obligation : Optional.empty();
    }

    @Override
    public Optional<CordaDDRObligationDTO> findRedeemByExternalId(String id) {
        Optional<CordaDDRObligationDTO> obligation = findObligationByExternalId(id);
        return obligation.isPresent() && obligation.get().getType().equals(DDRObligationType.REDEEM) ?
                obligation : Optional.empty();
    }

    @Override
    public Optional<String> createPledge(long amount) {
        String url = obligationsURL + "request-pledge";
        HttpEntity<ObligationRequestDTO> request = new HttpEntity<>(new ObligationRequestDTO(amount));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> cancelPledge(String externalId) {
        String url = obligationsURL + "cancel-pledge";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> denyPledge(String externalId) {
        String url = obligationsURL + "deny-pledge";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> approvePledge(String externalId) {
        String url = obligationsURL + "approve-pledge";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> createRedeem(long amount) {
        String url = obligationsURL + "request-redeem";
        HttpEntity<ObligationRequestDTO> request = new HttpEntity<>(new ObligationRequestDTO(amount));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> cancelRedeem(String externalId) {
        String url = obligationsURL + "cancel-redeem";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> denyRedeem(String externalId) {
        String url = obligationsURL + "deny-redeem";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> approveRedeem(String externalId) {
        String url = obligationsURL + "approve-redeem";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return Optional.ofNullable(response.getBody());
    }
}
