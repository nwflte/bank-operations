package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.dto.ObligationRequestDTO;
import com.octo.bankoperations.dto.ObligationUpdateDTO;
import com.octo.bankoperations.service.ObligationService;
import com.octo.bankoperations.service.StateStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.octo.bankoperations.CONSTANTS.CORDA_URL;

@Service
public class ObligationServiceVaultImpl implements ObligationService {

    private static final String OBLIGATIONS_URL = CORDA_URL + "/api/obligations/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<CordaDDRObligationDTO> loadAll(StateStatus stateStatus) {
        ResponseEntity<List<CordaDDRObligationDTO>> response = restTemplate.exchange(OBLIGATIONS_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaDDRObligationDTO>>(){});
        return response.getBody();
    }

    @Override
    public Optional<CordaDDRObligationDTO> findById(String id) {
        String url = OBLIGATIONS_URL + id;
        ResponseEntity<CordaDDRObligationDTO> response = restTemplate.getForEntity(url, CordaDDRObligationDTO.class);
        return Optional.ofNullable(response.getBody());
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
