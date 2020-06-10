package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.dto.NodeInfo;
import com.octo.bankoperations.service.NodeService;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {

    private final String nodeURL;
    private final KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    public NodeServiceImpl(KeycloakRestTemplate keycloakRestTemplate, Constants constants) {
        this.keycloakRestTemplate = keycloakRestTemplate;
        nodeURL = constants.getNodeUrl() + "/api/node/";
    }

    @Override
    public NodeInfo whoami() {
        String url = nodeURL + "me";
        ResponseEntity<NodeInfo> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null, NodeInfo.class);
        return response.getBody();
    }

    @Override
    public List<String> getPeers() {
        String url = nodeURL + "peers";
        ResponseEntity<List<String>> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }

    @Override
    public List<String> myLegalIdentities() {
        String url = nodeURL + "legalIds";
        ResponseEntity<List<String>> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }

    @Override
    public List<String> myAddresses() {
        String url = nodeURL + "addresses";
        ResponseEntity<List<String>> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {
                });
        return response.getBody();
    }

    @Override
    public int platformVersion() {
        String url = nodeURL + "platform-version";
        ResponseEntity<Integer> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null, Integer.class);
        return response.getBody();
    }

    @Override
    public Instant currentNodeTime() {
        String url = nodeURL + "node-time";
        ResponseEntity<Instant> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null, Instant.class);
        return response.getBody();
    }

    @Override
    public List<String> notaryIdentities() {
        String url = nodeURL + "notaries";
        ResponseEntity<List<String>> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }

    @Override
    public List<NodeInfo> networkMapSnapshot() {
        String url = nodeURL + "network-map";
        ResponseEntity<List<NodeInfo>> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<NodeInfo>>() {});
        return response.getBody();
    }

    @Override
    public List<String> registeredFlows() {
        String url = nodeURL + "registered-flows";
        ResponseEntity<List<String>> response = keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }
}
