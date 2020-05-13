package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaIntraBankTransferDTO;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.service.IntraBankTransferService;
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
public class IntraBankTransferServiceVaultImpl implements IntraBankTransferService {

    private final String INTRA_BANK_URL ;
    private final KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    public IntraBankTransferServiceVaultImpl(KeycloakRestTemplate keycloakRestTemplate, Constants constants) {
        this.keycloakRestTemplate = keycloakRestTemplate;
        INTRA_BANK_URL = constants.getNodeUrl() + "/api/intra/";
    }

    @Override
    public List<CordaIntraBankTransferDTO> loadAll() {
        ResponseEntity<List<CordaIntraBankTransferDTO>> response = keycloakRestTemplate.exchange(INTRA_BANK_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaIntraBankTransferDTO>>(){});
        return response.getBody();
    }

    @Override
    public Optional<CordaIntraBankTransferDTO> findById(String id) {
        String url = INTRA_BANK_URL + id;
        ResponseEntity<CordaIntraBankTransferDTO> response = keycloakRestTemplate.getForEntity(url, CordaIntraBankTransferDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> transfer(BankTransferDTO dto) {
        String url = INTRA_BANK_URL + "record-transfer";
        dto.setStatus(VirementStatus.INTERNE_PENDING_SAVE_IN_CORDA);
        HttpEntity<BankTransferDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> response = keycloakRestTemplate.exchange(url, HttpMethod.POST ,request, String.class);
        return Optional.ofNullable(response.getBody());
    }
}
