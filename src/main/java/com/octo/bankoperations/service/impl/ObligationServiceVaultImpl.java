package com.octo.bankoperations.service.impl;

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

import static com.octo.bankoperations.CONSTANTS.CORDA_URL;

@Service
public class ObligationServiceVaultImpl implements ObligationService {

    private static final String OBLIGATIONS_URL = CORDA_URL + "/api/obligations/";
    private final KeycloakRestTemplate restTemplate;

    @Autowired
    public ObligationServiceVaultImpl(KeycloakRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CordaDDRObligationDTO> loadAllObligations(StateStatus stateStatus) {
        ResponseEntity<List<CordaDDRObligationDTO>> response = restTemplate.exchange(OBLIGATIONS_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaDDRObligationDTO>>(){});
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
    public Optional<CordaDDRObligationDTO> findObligationById(String id) {
        String url = OBLIGATIONS_URL + id;
        ResponseEntity<CordaDDRObligationDTO> response = restTemplate.getForEntity(url, CordaDDRObligationDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<CordaDDRObligationDTO> findPledgeById(String id) {
        Optional<CordaDDRObligationDTO> obligation = findObligationById(id);
        return obligation.isPresent() && obligation.get().getType().equals(DDRObligationType.PLEDGE) ?
                obligation : Optional.empty();
    }

    @Override
    public Optional<CordaDDRObligationDTO> findRedeemById(String id) {
        Optional<CordaDDRObligationDTO> obligation = findObligationById(id);
        return obligation.isPresent() && obligation.get().getType().equals(DDRObligationType.REDEEM) ?
                obligation : Optional.empty();
    }

    @Override
    public Optional<String> createPledge(long amount) {
        String url = OBLIGATIONS_URL + "request-pledge";
        HttpEntity<ObligationRequestDTO> request = new HttpEntity<>(new ObligationRequestDTO(amount));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST ,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> cancelPledge(String externalId) {
        String url = OBLIGATIONS_URL + "cancel-pledge";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> denyPledge(String externalId) {
        String url = OBLIGATIONS_URL + "deny-pledge";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> approvePledge(String externalId) {
        String url = OBLIGATIONS_URL + "approve-pledge";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> createRedeem(long amount) {
        String url = OBLIGATIONS_URL + "request-redeem";
        HttpEntity<ObligationRequestDTO> request = new HttpEntity<>(new ObligationRequestDTO(amount));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST ,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> cancelRedeem(String externalId) {
        String url = OBLIGATIONS_URL + "cancel-redeem";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> denyRedeem(String externalId) {
        String url = OBLIGATIONS_URL + "deny-redeem";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> approveRedeem(String externalId) {
        String url = OBLIGATIONS_URL + "approve-redeem";
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO(externalId));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        return Optional.ofNullable(response.getBody());
    }
}
