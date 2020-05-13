package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaInterBankTransferDTO;
import com.octo.bankoperations.enums.VirementStatus;
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

@Service
public class InterBankTransferServiceVaultImpl implements InterBankTransferService {

    private final String INTER_BANK_URL;

    private final KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    public InterBankTransferServiceVaultImpl(KeycloakRestTemplate template, Constants constants) {
        this.keycloakRestTemplate = template;
        INTER_BANK_URL = constants.getNodeUrl() + "/api/inter/";
    }

    @Override
    public List<CordaInterBankTransferDTO> loadAll() {
        ResponseEntity<List<CordaInterBankTransferDTO>> response = keycloakRestTemplate.exchange(INTER_BANK_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaInterBankTransferDTO>>(){});
        return response.getBody();
    }

    @Override
    public Optional<CordaInterBankTransferDTO> findById(String id) {
        String url = INTER_BANK_URL + id;
        ResponseEntity<CordaInterBankTransferDTO> response = keycloakRestTemplate.getForEntity(url, CordaInterBankTransferDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> transfer(BankTransferDTO dto) {
        String url = INTER_BANK_URL + "perform-transfer";
        dto.setStatus(VirementStatus.EXTERNE_PENDING_APPROVAL);
        HttpEntity<BankTransferDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST ,request, String.class);
        return Optional.ofNullable(response.getBody());
    }
}
