package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaInterBankTransferDTO;
import com.octo.bankoperations.service.InterBankTransferService;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.octo.bankoperations.CONSTANTS.CORDA_URL;

@Service
public class InterBankTransferServiceVaultImpl implements InterBankTransferService {

    private static final String INTER_BANK_URL = CORDA_URL + "/api/inter/";

    private final KeycloakRestTemplate restTemplate;

    @Autowired
    public InterBankTransferServiceVaultImpl(KeycloakRestTemplate template) {
        this.restTemplate = template;
    }

    @Override
    public List<CordaInterBankTransferDTO> loadAll() {
        ResponseEntity<List<CordaInterBankTransferDTO>> response = restTemplate.exchange(INTER_BANK_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaInterBankTransferDTO>>(){});
        return response.getBody();
    }

    @Override
    public Optional<CordaInterBankTransferDTO> findById(String id) {
        String url = INTER_BANK_URL + id;
        ResponseEntity<CordaInterBankTransferDTO> response = restTemplate.getForEntity(url, CordaInterBankTransferDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> transfer(BankTransferDTO dto) {
        String url = INTER_BANK_URL + "perform-transfer";
        HttpEntity<BankTransferDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST ,request, String.class);
        return Optional.ofNullable(response.getBody());
    }
}
