package com.octo.bankoperations.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.dto.ClientDTO;
import com.octo.bankoperations.mapper.ClientMapper;
import com.octo.bankoperations.mapper.CompteMapper;
import com.octo.bankoperations.service.ClientService;
import com.octo.bankoperations.service.VirementService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ClientControllerTest {

    private static final String REQUEST_PATH = "/api/clients";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private VirementService virementService;

    @Test
    @WithMockUser
    void whenRequestByIdAndClientExists_thenReturns200AndClientDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1";

        given(clientService.getById(1L)).willReturn(Optional.of(ModelsUtil.createClient(1L)));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(ClientMapper.map(ModelsUtil.createClient(1L)));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(clientService, times(1)).getById(1L);
    }

    @Test
    @WithMockUser
    void whenRequestByIdAndClientNotExists_thenReturns404() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1";

        given(clientService.getById(1L)).willReturn(Optional.empty());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Client could not be found with id: 1", actualResponseBody);
        verify(clientService, times(1)).getById(1L);
    }


    @Test
    @WithMockUser
    void whenloadAll_thenReturns200AndListClientDTO() throws Exception {
        List<Client> expectedList = Arrays.asList(ModelsUtil.createClient(1L), ModelsUtil.createClient(2L));
        given(clientService.getAll()).willReturn(expectedList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REQUEST_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedList.stream().map(ClientMapper::map));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(clientService, times(1)).getAll();
    }

    @Test
    @WithMockUser
    void whenCreateClient_ThenReturns201AndClientDTO() throws Exception {
        ClientDTO clientDTO = ClientMapper.map(ModelsUtil.createClient(3L));
        given(clientService.save(any(ClientDTO.class))).willReturn(ModelsUtil.createClient(3L));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(REQUEST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(clientDTO);

        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    @WithMockUser
    void whenRequestCompteByClientIdAndClientExists_thenReturns200AndCompteDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1/compte";

        given(clientService.getById(1L)).willReturn(Optional.of(ModelsUtil.createClient(1L)));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(CompteMapper.map(ModelsUtil
                .createClient(1L).getCompte()));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(clientService, times(1)).getById(1L);
    }

    @Test
    @WithMockUser
    void whenRequestCompteByBadClientId_thenReturns404() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1/compte";

        given(clientService.getById(1L)).willReturn(Optional.empty());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Client could not be found with id: 1", actualResponseBody);
        verify(clientService, times(1)).getById(1L);
    }

}