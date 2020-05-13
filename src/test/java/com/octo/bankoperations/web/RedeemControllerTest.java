package com.octo.bankoperations.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.service.ObligationService;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static com.octo.bankoperations.enums.DDRObligationType.REDEEM;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RedeemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class RedeemControllerTest {

    private static final String REQUEST_PATH = "/api/obligations/redeems";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ObligationService obligationService;

    @Test
    @WithMockUser
    void whenRequestByIdAndRedeemExists_thenReturns200AndObligationDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/EXTERNAL_ID";
        given(obligationService.findRedeemByExternalId("EXTERNAL_ID"))
                .willReturn(Optional.of(ModelsUtil.createObligation(REDEEM, REQUEST, "EXTERNAL_ID")));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper
                .writeValueAsString(ModelsUtil.createObligation(REDEEM, REQUEST, "EXTERNAL_ID"));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(obligationService, times(1)).findRedeemByExternalId("EXTERNAL_ID");
    }

    @Test
    @WithMockUser
    void whenRequestByIdAndRedeemNotExists_thenReturns200AndObligationDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/EXTERNAL_ID";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Obligation could not be found with id: EXTERNAL_ID", actualResponseBody);
        verify(obligationService, times(1)).findRedeemByExternalId("EXTERNAL_ID");
    }

    @Test
    @WithMockUser
    void whenloadAll_thenReturns200AndListObligationDTO() throws Exception {
        List<CordaDDRObligationDTO> expectedList = Arrays.asList(ModelsUtil.createObligation(REDEEM, REQUEST, "EXTERNAL_ID"),
                ModelsUtil.createObligation(REDEEM, REQUEST, "EXTERNAL_ID"));
        given(obligationService.loadAllRedeems(any())).willReturn(expectedList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REQUEST_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedList);
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(obligationService, times(1)).loadAllRedeems(any());
    }
}