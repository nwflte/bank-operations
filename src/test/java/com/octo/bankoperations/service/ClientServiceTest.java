package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.dto.ClientDTO;
import com.octo.bankoperations.enums.Gender;
import com.octo.bankoperations.repository.ClientRepository;
import com.octo.bankoperations.service.impl.ClientServiceImpl;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void getAllTest() {
        List<Client> expectedList = Arrays.asList(ModelsUtil.createClient( 1L), ModelsUtil.createClient( 2L));
        given(clientRepository.findAll()).willReturn(expectedList);

        final List<Client> actualList = clientService.getAll();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(2, actualList.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(ModelsUtil.createClient(1L)));

        final Optional<Client> actualClient = clientService.getById(1L);

        Assertions.assertTrue(actualClient.isPresent());
        Assertions.assertEquals(1L, actualClient.get().getId());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void getByBadIdTest() {
        given(clientRepository.findById(1L)).willReturn(Optional.empty());

        final Optional<Client> actualClient = clientService.getById(1L);

        Assertions.assertFalse(actualClient.isPresent());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void saveTest() {
        ClientDTO dto = new ClientDTO(3L, "user", Gender.MALE, "lastname", "firstname", "email@gmail.com", "Casa", "adresse1",
                "adresse2", Date.from(Instant.parse("2020-05-01T15:23:01Z")));
        given(clientRepository.save(Mockito.any(Client.class))).willReturn(ModelsUtil.createClient(3L));

        final Client actualCompte = clientService.save(dto);

        Assertions.assertNotNull(actualCompte);
        Assertions.assertEquals(3L, actualCompte.getId());
        verify(clientRepository, times(1)).save(Mockito.any());
    }
}