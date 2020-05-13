package com.octo.bankoperations.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.VirementDTO;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.mapper.VirementMapper;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VirementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class VirementControllerTest {

    private static final String REQUEST_PATH = "/api/virements";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VirementService virementServiceMock;

    @Test
    @WithMockUser
    void whenRequestByIdAndVirementExists_thenReturns200AndVirementDTO() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1";

        given(virementServiceMock.findById(1L)).willReturn(Optional.of(ModelsUtil.createVirement()));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(VirementMapper.map(ModelsUtil.createVirement()));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(virementServiceMock, times(1)).findById(1L);
    }

    @Test
    @WithMockUser
    void whenRequestByIdAndVirementNotExists_thenReturns404() throws Exception {
        String requestPathWithId = REQUEST_PATH + "/1";

        given(virementServiceMock.findById(1L)).willReturn(Optional.empty());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(requestPathWithId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        verify(virementServiceMock, times(1)).findById(1L);
    }


    @Test
    @WithMockUser
    void whenloadAll_thenReturns200AndListVirementDTO() throws Exception {
        List<Virement> expectedList = Arrays.asList(ModelsUtil.createVirement(), ModelsUtil.createVirement());
        given(virementServiceMock.loadAll()).willReturn(expectedList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REQUEST_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedList.stream().map(VirementMapper::map));
        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
        verify(virementServiceMock, times(1)).loadAll();
    }

    @Test
    @WithMockUser
    void whenCreateVirement_ThenReturns201AndVirementDTO() throws Exception {
        VirementDTO virementDTO = new VirementDTO(null, null, "007111111111111111111111", "008111111111111111111111", "MOTIF",
                BigDecimal.valueOf(100), Date.from(Instant.parse("2020-05-01T15:23:01Z")), null, null);
        Virement expectedVirement = ModelsUtil.createVirement(1L, "REF", BigDecimal.valueOf(100), Date.from(Instant.parse("2020-05-01T15:23:01Z")),
                "MOTIF", "007111111111111111111111", "008111111111111111111111",
                Date.from(Instant.parse("2020-05-01T15:23:01Z")), VirementStatus.EXTERNE_PENDING_APPROVAL);
        given(virementServiceMock.virement(any())).willReturn(expectedVirement);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(REQUEST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(virementDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        virementDTO.setId(1L);
        virementDTO.setStatus(VirementStatus.EXTERNE_PENDING_APPROVAL);
        virementDTO.setDateUpdateStatus(Date.from(Instant.parse("2020-05-01T15:23:01Z")));
        virementDTO.setReference("REF");
        String expectedResponseBody = objectMapper.writeValueAsString(virementDTO);

        Assertions.assertEquals(expectedResponseBody, actualResponseBody);
    }

}