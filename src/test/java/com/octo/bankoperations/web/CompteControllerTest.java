package com.octo.bankoperations.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.dto.CompteDTO;
import com.octo.bankoperations.mapper.CompteMapper;
import com.octo.bankoperations.service.CompteService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CompteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CompteControllerTest {

    private static final String REQUEST_PATH = "/api/comptes";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompteService compteService;

    @Test
    @WithMockUser
    void whenRequestByIdAndCompteExists_thenReturns200AndCompteDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1";

        given(compteService.getById(1L)).willReturn(Optional.of(ModelsUtil
                .createCompte("007111111111111111111111", 3L, BigDecimal.valueOf(100), 1L)));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(CompteMapper.map(ModelsUtil
                .createCompte("007111111111111111111111", 3L, BigDecimal.valueOf(100), 1L)));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(compteService, times(1)).getById(1L);
    }

    @Test
    @WithMockUser
    void whenRequestByIdAndCompteNotExists_thenReturns404() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1";

        given(compteService.getById(1L)).willReturn(Optional.empty());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Compte could not be found with id: 1", actualResponseBody);
        verify(compteService, times(1)).getById(1L);
    }


    @Test
    @WithMockUser
    void whenloadAll_thenReturns200AndListCompteDTO() throws Exception {
        Compte compte = ModelsUtil.createCompte("007111111111111111111111", 3L, BigDecimal.valueOf(100), 1L);
        List<Compte> expectedList = Arrays.asList(compte, compte);
        given(compteService.getAll()).willReturn(expectedList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REQUEST_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedList.stream().map(CompteMapper::map));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(compteService, times(1)).getAll();
    }

    @Test
    @WithMockUser
    void whenCreateCompte_ThenReturns201AndCompteDTO() throws Exception {
        Compte expectedCompte = ModelsUtil.createCompte("007111111111111111111111", 3L);
        CompteDTO compteDTO = CompteMapper.map(expectedCompte);
        given(compteService.save(any())).willReturn(expectedCompte);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(REQUEST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compteDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(compteDTO);

        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    void whenCheckIfCompteExistsByRIB_ThenReturns200AndTrue() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/exists/007111111111111111111111";

        given(compteService.existsByRIB("007111111111111111111111")).willReturn(true);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("true", actualResponseBody);
        verify(compteService, times(1)).existsByRIB("007111111111111111111111");
    }

    @Test
    void whenCheckIfCompteExistsByBadRIB_ThenReturns200AndFalse() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/exists/007111111111111111111111";

        given(compteService.existsByRIB("007111111111111111111111")).willReturn(false);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("false", actualResponseBody);
        verify(compteService, times(1)).existsByRIB("007111111111111111111111");
    }

    @Test
    @WithMockUser
    void whenRequestByClientIdAndCompteExists_thenReturns200AndCompteDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/client/1";

        given(compteService.getComptesForClient(1L)).willReturn(Optional.of(ModelsUtil
                .createCompte("007111111111111111111111", 1L, BigDecimal.valueOf(100), 1L)));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(CompteMapper.map(ModelsUtil
                .createCompte("007111111111111111111111", 1L, BigDecimal.valueOf(100), 1L)));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(compteService, times(1)).getComptesForClient(1L);
    }

    @Test
    @WithMockUser
    void whenRequestByBadClientId_thenReturns404() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/client/1";

        given(compteService.getComptesForClient(1L)).willReturn(Optional.empty());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Compte could not be found", actualResponseBody);
        verify(compteService, times(1)).getComptesForClient(1L);
    }

}