package com.octo.bankoperations.service;

import com.octo.bankoperations.Constants;
import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.dto.ObligationRequestDTO;
import com.octo.bankoperations.dto.ObligationUpdateDTO;
import com.octo.bankoperations.enums.DDRObligationStatus;
import com.octo.bankoperations.enums.DDRObligationType;
import com.octo.bankoperations.enums.StateStatus;
import com.octo.bankoperations.service.impl.ObligationServiceVaultImpl;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.octo.bankoperations.enums.DDRObligationStatus.REQUEST;
import static com.octo.bankoperations.enums.DDRObligationType.PLEDGE;
import static com.octo.bankoperations.enums.DDRObligationType.REDEEM;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Constants.class, ObligationServiceVaultImpl.class})
@ActiveProfiles("banka")
@EnableConfigurationProperties
class ObligationServiceTest {

    @MockBean
    private KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    private Constants constants;

    @Autowired
    private ObligationService obligationService;

    private String url;

    @BeforeEach
    public void setup(){
        url = constants.getNodeUrl() + "/api/obligations/";
        List<CordaDDRObligationDTO> expectedList = Arrays.asList(ModelsUtil.createObligation(PLEDGE, REQUEST),
                ModelsUtil.createObligation(REDEEM, REQUEST));
        given(keycloakRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CordaDDRObligationDTO>>(){}))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(expectedList));
    }

    @ParameterizedTest
    @EnumSource(value = StateStatus.class)
    void loadAllObligations(StateStatus stateStatus) {
        List<CordaDDRObligationDTO> actualList = obligationService.loadAllObligations(stateStatus);

        Assertions.assertEquals(2, actualList.size());
        verify(keycloakRestTemplate, times(1)).exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CordaDDRObligationDTO>>(){});
    }

    @Test
    void loadAllPledges() {
        List<CordaDDRObligationDTO> actualList = obligationService.loadAllPledges(StateStatus.ALL);

        Assertions.assertEquals(1, actualList.size());
    }

    @Test
    void loadAllRedeems() {
        List<CordaDDRObligationDTO> actualList = obligationService.loadAllRedeems(StateStatus.ALL);

        Assertions.assertEquals(1, actualList.size());
    }

    @ParameterizedTest
    @EnumSource(DDRObligationType.class)
    void findObligationByExternalId(DDRObligationType type) {
        String id = "OBLIG_ID";
        CordaDDRObligationDTO expectedObligation = ModelsUtil.createObligation(type, REQUEST,id);
        given(keycloakRestTemplate.getForEntity(url+id, CordaDDRObligationDTO.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(expectedObligation));

        Optional<CordaDDRObligationDTO> actualObligation = obligationService.findObligationByExternalId(id);

        Assertions.assertTrue(actualObligation.isPresent());
        Assertions.assertEquals(id, actualObligation.get().getExternalId());
        Assertions.assertEquals(type, actualObligation.get().getType());
        Assertions.assertEquals(REQUEST, actualObligation.get().getStatus());
    }

    @ParameterizedTest
    @EnumSource(DDRObligationStatus.class)
    void findPledgeByExternalId(DDRObligationStatus status) {
        String id = "OBLIG_ID";
        CordaDDRObligationDTO expectedObligation = ModelsUtil.createObligation(PLEDGE, status,id);
        given(keycloakRestTemplate.getForEntity(url+id, CordaDDRObligationDTO.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(expectedObligation));

        Optional<CordaDDRObligationDTO> actualObligation = obligationService.findObligationByExternalId(id);

        Assertions.assertTrue(actualObligation.isPresent());
        Assertions.assertEquals(id, actualObligation.get().getExternalId());
        Assertions.assertEquals(PLEDGE, actualObligation.get().getType());
        Assertions.assertEquals(status, actualObligation.get().getStatus());
    }

    @ParameterizedTest
    @EnumSource(DDRObligationStatus.class)
    void findRedeemByExternalId(DDRObligationStatus status) {
        String id = "OBLIG_ID";
        CordaDDRObligationDTO expectedObligation = ModelsUtil.createObligation(REDEEM, status,id);
        given(keycloakRestTemplate.getForEntity(url+id, CordaDDRObligationDTO.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body(expectedObligation));

        Optional<CordaDDRObligationDTO> actualObligation = obligationService.findObligationByExternalId(id);

        Assertions.assertTrue(actualObligation.isPresent());
        Assertions.assertEquals(id, actualObligation.get().getExternalId());
        Assertions.assertEquals(REDEEM, actualObligation.get().getType());
        Assertions.assertEquals(status, actualObligation.get().getStatus());
    }

    @Test
    void createPledge() {
        HttpEntity<ObligationRequestDTO> request = new HttpEntity<>(new ObligationRequestDTO(10));
        given(keycloakRestTemplate.exchange(url+"request-pledge", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.createPledge(10);

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"request-pledge", HttpMethod.POST ,request, String.class);
    }

    @Test
    void cancelPledge() {
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO("EXTERNAL_ID"));
        given(keycloakRestTemplate.exchange(url+"cancel-pledge", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.cancelPledge("EXTERNAL_ID");

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"cancel-pledge", HttpMethod.POST ,request, String.class);
    }

    @Test
    void denyPledge() {
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO("EXTERNAL_ID"));
        given(keycloakRestTemplate.exchange(url+"deny-pledge", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.denyPledge("EXTERNAL_ID");

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"deny-pledge", HttpMethod.POST ,request, String.class);
    }

    @Test
    void approvePledge() {
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO("EXTERNAL_ID"));
        given(keycloakRestTemplate.exchange(url+"approve-pledge", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.approvePledge("EXTERNAL_ID");

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"approve-pledge", HttpMethod.POST ,request, String.class);
    }

    @Test
    void createRedeem() {
        HttpEntity<ObligationRequestDTO> request = new HttpEntity<>(new ObligationRequestDTO(10));
        given(keycloakRestTemplate.exchange(url+"request-redeem", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.createRedeem(10);

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"request-redeem", HttpMethod.POST ,request, String.class);
    }

    @Test
    void cancelRedeem() {
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO("EXTERNAL_ID"));
        given(keycloakRestTemplate.exchange(url+"cancel-redeem", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.cancelRedeem("EXTERNAL_ID");

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"cancel-redeem", HttpMethod.POST ,request, String.class);
    }

    @Test
    void denyRedeem() {
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO("EXTERNAL_ID"));
        given(keycloakRestTemplate.exchange(url+"deny-redeem", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.denyRedeem("EXTERNAL_ID");

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"deny-redeem", HttpMethod.POST ,request, String.class);
    }

    @Test
    void approveRedeem() {
        HttpEntity<ObligationUpdateDTO> request = new HttpEntity<>(new ObligationUpdateDTO("EXTERNAL_ID"));
        given(keycloakRestTemplate.exchange(url+"approve-redeem", HttpMethod.POST ,request, String.class))
                .willReturn(ResponseEntity.status(HttpStatus.OK).body("Signed Transaction"));

        Optional<String> actualResponse = obligationService.approveRedeem("EXTERNAL_ID");

        Assertions.assertTrue(actualResponse.isPresent());
        Assertions.assertEquals("Signed Transaction", actualResponse.get());

        verify(keycloakRestTemplate, times(1)).exchange(url+"approve-redeem", HttpMethod.POST ,request, String.class);
    }
}