package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.service.DDRService;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DDRServiceVaultImpl implements DDRService {

    private final String ddrUrl;
    private final KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    public DDRServiceVaultImpl(KeycloakRestTemplate template, Constants constants) {
        this.keycloakRestTemplate = template;
        ddrUrl = constants.getNodeUrl() + "/api/ddrs/";
    }

    @Override
    public Long balance() {
        String url = ddrUrl + "balance";
        ResponseEntity<Long> response = keycloakRestTemplate.getForEntity(url, Long.class);
        return response.getBody();
    }

    @Override
    public Long count() {
        String url = ddrUrl + "count";
        ResponseEntity<Long> response = keycloakRestTemplate.getForEntity(url, Long.class);
        return response.getBody();
    }

    @Override
    public Double average() {
        String url = ddrUrl + "average";
        ResponseEntity<Double> response = keycloakRestTemplate.getForEntity(url, Double.class);
        return response.getBody();
    }
}
