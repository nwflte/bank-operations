package com.octo.bankoperations.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.enums.DDRObligationType;
import com.octo.bankoperations.service.ObligationService;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.octo.bankoperations.enums.DDRObligationStatus.REQUEST;
import static com.octo.bankoperations.enums.DDRObligationType.PLEDGE;
import static com.octo.bankoperations.enums.DDRObligationType.REDEEM;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ObligationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ObligationControllerTest {

    private static final String REQUEST_PATH = "/api/obligations";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ObligationService obligationService;

    @WithMockUser
    @ParameterizedTest
    @EnumSource(value = DDRObligationType.class)
    void whenRequestByIdAndObligationExists_thenReturns200AndObligationDTO(DDRObligationType type) throws Exception {
        String requestPathWithId = REQUEST_PATH + "/EXTERNAL_ID";
        given(obligationService.findObligationByExternalId("EXTERNAL_ID"))
                .willReturn(Optional.of(ModelsUtil.createObligation(type, REQUEST, "EXTERNAL_ID")));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper
                .writeValueAsString(ModelsUtil.createObligation(type, REQUEST, "EXTERNAL_ID"));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(obligationService, times(1)).findObligationByExternalId("EXTERNAL_ID");
    }

    @ParameterizedTest
    @EnumSource(value = DDRObligationType.class)
    void whenRequestByIdAndObligationNotExists_thenReturns200AndObligationDTO(DDRObligationType type) throws Exception {
        String requestPathWithId = REQUEST_PATH + "/EXTERNAL_ID";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Obligation could not be found with id: EXTERNAL_ID", actualResponseBody);
        verify(obligationService, times(1)).findObligationByExternalId("EXTERNAL_ID");
    }

    @Test
    @WithMockUser
    void whenLoadAll_thenReturns200AndListObligationDTO() throws Exception {
        List<CordaDDRObligationDTO> expectedList = Arrays.asList(ModelsUtil.createObligation(REDEEM, REQUEST, "EXTERNAL_ID"),
                ModelsUtil.createObligation(PLEDGE, REQUEST, "EXTERNAL_ID"));
        given(obligationService.loadAllObligations(any())).willReturn(expectedList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REQUEST_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedList);
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(obligationService, times(1)).loadAllObligations(any());
    }
}