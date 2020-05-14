package com.octo.bankoperations.service;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.CordaInterBankTransferDTO;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.service.impl.InterBankTransferServiceVaultImpl;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Constants.class, InterBankTransferServiceVaultImpl.class})
@ActiveProfiles("banka")
@EnableConfigurationProperties
class InterBankTransferServiceTest {

    @MockBean
    private KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    private Constants constants;

    @Autowired
    private InterBankTransferService interBankTransferService;

    private String url;

    @BeforeEach
    public void setup() {
        url = constants.getNodeUrl() + "/api/inter/";
    }

    @Test
    void loadAll() {
        List<CordaInterBankTransferDTO> expectedList = Arrays.asList(ModelsUtil.createInterBankTransfer(),
                ModelsUtil.createInterBankTransfer());
        given(keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaInterBankTransferDTO>>() {
                }))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(expectedList));


        List<CordaInterBankTransferDTO> actualList = interBankTransferService.loadAll();

        Assertions.assertEquals(2, actualList.size());
        verify(keycloakRestTemplate, times(1)).exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CordaInterBankTransferDTO>>() {
                });
    }

    @Test
    void findById() {
        String id = "OBLIG_ID";
        CordaInterBankTransferDTO expectedTransfer = ModelsUtil.createInterBankTransfer(id);
        given(keycloakRestTemplate.getForEntity(url + id, CordaInterBankTransferDTO.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(expectedTransfer));

        Optional<CordaInterBankTransferDTO> actualTransfer = interBankTransferService.findById(id);

        Assertions.assertTrue(actualTransfer.isPresent());
        Assertions.assertEquals(id, actualTransfer.get().getExternalId());
        verify(keycloakRestTemplate, times(1)).getForEntity(url + id, CordaInterBankTransferDTO.class);
    }

    @Test
    void transfer() {
        BankTransferDTO dto = new BankTransferDTO("REF", "00811111111111111111111", "00711111111111111111111",
                BigDecimal.valueOf(100), Date.from(Instant.parse("2020-05-01T15:23:01Z")), VirementStatus.EXTERNE_PENDING_APPROVAL, null);
        HttpEntity<BankTransferDTO> request = new HttpEntity<>(dto);
        given(keycloakRestTemplate.exchange(url + "perform-transfer", HttpMethod.POST, request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = interBankTransferService.transfer(dto);
        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url + "perform-transfer", HttpMethod.POST, request, String.class);
    }
}