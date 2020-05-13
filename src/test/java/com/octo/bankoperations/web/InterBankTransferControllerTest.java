package com.octo.bankoperations.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.dto.CordaInterBankTransferDTO;
import com.octo.bankoperations.service.InterBankTransferService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = InterBankTransferController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class InterBankTransferControllerTest {

    private static final String REQUEST_PATH = "/api/inter";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InterBankTransferService transferService;

    @WithMockUser
    @Test
    void whenRequestByIdAndInterTransferExists_thenReturns200AndInterTransferDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/EXTERNAL_ID";
        given(transferService.findById("EXTERNAL_ID"))
                .willReturn(Optional.of(ModelsUtil.createInterBankTransfer("EXTERNAL_ID")));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper
                .writeValueAsString(ModelsUtil.createInterBankTransfer("EXTERNAL_ID"));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(transferService, times(1)).findById("EXTERNAL_ID");
    }

    @Test
    void whenRequestByIdAndInterTransferNotExists_thenReturns200AndInterTransferDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/EXTERNAL_ID";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Transfer could not be found with id: EXTERNAL_ID", actualResponseBody);
        verify(transferService, times(1)).findById("EXTERNAL_ID");
    }

    @Test
    @WithMockUser
    void whenLoadAll_thenReturns200AndListInterTransferDTO() throws Exception {
        List<CordaInterBankTransferDTO> expectedList = Arrays.asList(ModelsUtil.createInterBankTransfer("EXTERNAL_ID"),
                ModelsUtil.createInterBankTransfer("EXTERNAL_ID"));
        given(transferService.loadAll()).willReturn(expectedList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REQUEST_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedList);
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(transferService, times(1)).loadAll();
    }
}