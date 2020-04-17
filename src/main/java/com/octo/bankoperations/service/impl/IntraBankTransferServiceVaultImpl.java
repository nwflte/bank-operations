package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaIntraBankTransferDTO;
import com.octo.bankoperations.service.IntraBankTransferService;
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
public class IntraBankTransferServiceVaultImpl implements IntraBankTransferService {

    private static final String INTRA_BANK_URL = CORDA_URL + "/api/intra/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<CordaIntraBankTransferDTO> loadAll() {
        ResponseEntity<List<CordaIntraBankTransferDTO>> response = restTemplate.exchange(INTRA_BANK_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaIntraBankTransferDTO>>(){});
        return response.getBody();
    }

    @Override
    public Optional<CordaIntraBankTransferDTO> findById(String id) {
        String url = INTRA_BANK_URL + id;
        ResponseEntity<CordaIntraBankTransferDTO> response = restTemplate.getForEntity(url, CordaIntraBankTransferDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public Optional<String> transfer(BankTransferDTO dto) {
        String url = INTRA_BANK_URL + "record-transfer";
        HttpEntity<BankTransferDTO> request = new HttpEntity<>(dto);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST ,request, String.class);
        return Optional.ofNullable(response.getBody());
    }
}
