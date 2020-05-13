package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.dto.CompteDTO;
import com.octo.bankoperations.exceptions.ClientNotFoundException;
import com.octo.bankoperations.repository.ClientRepository;
import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.service.impl.CompteServiceImpl;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompteServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CompteServiceImpl compteService;

    @Test
    void getAllTest() {
        List<Compte> expectedList = Arrays.asList(ModelsUtil.createCompte("007111111111111111111111", 1L),
                ModelsUtil.createCompte("007111111111111111111112", 2L));
        given(compteRepository.findAll()).willReturn(expectedList);

        final List<Compte> actualList = compteService.getAll();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(2, actualList.size());
        verify(compteRepository, times(1)).findAll();
    }


    @Test
    void getByIdTest() {
        given(compteRepository.findById(1L)).willReturn(Optional.of(ModelsUtil.createCompte("007111111111111111111112", 1L, null, 1L)));

        final Optional<Compte> actualCompte = compteService.getById(1L);

        Assertions.assertTrue(actualCompte.isPresent());
        Assertions.assertEquals(1L, actualCompte.get().getId());
        verify(compteRepository, times(1)).findById(1L);
    }

    @Test
    void getByBadIdTest() {
        given(compteRepository.findById(1L)).willReturn(Optional.empty());

        final Optional<Compte> actualCompte = compteService.getById(1L);

        Assertions.assertFalse(actualCompte.isPresent());
        verify(compteRepository, times(1)).findById(1L);
    }

    @Test
    void findByRibTest() {
        given(compteRepository.findByRib("007111111111111111111112"))
                .willReturn(Optional.of(ModelsUtil.createCompte("007111111111111111111112", 1L)));

        final Optional<Compte> actualCompte = compteService.findByRib("007111111111111111111112");

        Assertions.assertTrue(actualCompte.isPresent());
        Assertions.assertEquals("007111111111111111111112", actualCompte.get().getRib());
        verify(compteRepository, times(1)).findByRib("007111111111111111111112");
    }

    @Test
    void findByBadRibTest() {
        given(compteRepository.findByRib("007111111111111111111112")).willReturn(Optional.empty());

        final Optional<Compte> actualCompte = compteService.findByRib("007111111111111111111112");

        Assertions.assertFalse(actualCompte.isPresent());
        verify(compteRepository, times(1)).findByRib("007111111111111111111112");
    }

    @Test
    void existsByRIBTest() {
        given(compteRepository.existsByRib("007111111111111111111112")).willReturn(true);

        final boolean actualResult = compteService.existsByRIB("007111111111111111111112");

        Assertions.assertTrue(actualResult);
        verify(compteRepository, times(1)).existsByRib("007111111111111111111112");
    }

    @Test
    void existsByBadRIBTest() {
        given(compteRepository.existsByRib("007111111111111111111112")).willReturn(false);

        final boolean actualResult = compteService.existsByRIB("007111111111111111111112");

        Assertions.assertFalse(actualResult);
        verify(compteRepository, times(1)).existsByRib("007111111111111111111112");
    }

    @Test
    void getComptesForClient() {
        given(compteRepository.findByClient(Mockito.any()))
                .willReturn(Optional.of(ModelsUtil.createCompte("007111111111111111111112", 3L, null, 1L)));
        given(clientRepository.findById(3L)).willReturn(Optional.of(ModelsUtil.createClient(3L)));

        final Optional<Compte> actualCompte = compteService.getComptesForClient(3L);

        Assertions.assertTrue(actualCompte.isPresent());
        Assertions.assertEquals(1L, actualCompte.get().getId());
        Assertions.assertEquals(3L, actualCompte.get().getClient().getId());
        Assertions.assertEquals("007111111111111111111112", actualCompte.get().getRib());
        verify(compteRepository, times(1)).findByClient(Mockito.any());
    }

    @Test
    void getComptesForClientNonExistantTest() {
        given(clientRepository.findById(3L)).willReturn(Optional.empty());

        ClientNotFoundException exception = Assertions.assertThrows(ClientNotFoundException.class,
                () -> compteService.getComptesForClient(3L));
        verify(clientRepository, times(1)).findById(3L);
    }

    @Test
    void saveTest() {
        CompteDTO dto = new CompteDTO(1L, "007111111111111111111112", BigDecimal.valueOf(100), false, 3L);
        given(compteRepository.save(Mockito.any(Compte.class)))
                .willReturn(ModelsUtil.createCompte("007111111111111111111112", 3L, null, 1L));

        given(clientRepository.findById(3L)).willReturn(Optional.of(ModelsUtil.createClient(3L)));

        final Compte actualCompte = compteService.save(dto);

        Assertions.assertNotNull(actualCompte);
        Assertions.assertEquals(1L, actualCompte.getId());
        Assertions.assertEquals(3L, actualCompte.getClient().getId());
        Assertions.assertEquals("007111111111111111111112", actualCompte.getRib());
        verify(compteRepository, times(1)).save(Mockito.any());
    }

    @Test
    void saveWithClientNonExistantTest() {
        CompteDTO dto = new CompteDTO(1L, "007111111111111111111112", BigDecimal.valueOf(100), false, 3L);
        given(clientRepository.findById(3L)).willReturn(Optional.empty());

        ClientNotFoundException exception = Assertions.assertThrows(ClientNotFoundException.class,
                () -> compteService.save(dto));
        verify(clientRepository, times(1)).findById(3L);
    }


}