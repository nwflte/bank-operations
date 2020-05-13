package com.octo.bankoperations.service;

import com.octo.bankoperations.amqp.AMQPSender;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.exceptions.CompteNonExistantException;
import com.octo.bankoperations.exceptions.IllegalReceivedVirementStatusException;
import com.octo.bankoperations.exceptions.SoldeDisponibleInsuffisantException;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.repository.VirementRepository;
import com.octo.bankoperations.service.impl.VirementServiceImpl;
import com.octo.bankoperations.utils.ModelsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.octo.bankoperations.utils.ModelsUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VirementServiceTest {
    @Mock
    private VirementRepository virementRepository;

    @Mock
    private CompteService compteService;

    @Mock
    private AMQPSender amqpSender;

    @InjectMocks
    private VirementServiceImpl virementService;

    @Test
    public void findAllTest() {
        given(virementRepository.findAll()).willReturn(Collections.singletonList(ModelsUtil.createVirement()));

        final List<Virement> actualList = virementService.loadAll();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(1, actualList.size());
        verify(virementRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest() {
        given(virementRepository.findById(VIREMENT_ID)).willReturn(Optional.of(ModelsUtil.createVirement()));

        final Optional<Virement> actualVirement = virementService.findById(VIREMENT_ID);

        Assertions.assertTrue(actualVirement.isPresent());
        Assertions.assertEquals(VIREMENT_REF, actualVirement.get().getReference());
        verify(virementRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void findAllForClientTest() {
        final String rib = "007787009200000000000000";
        given(compteService.getComptesForClient(1L)).willReturn(Optional.of(ModelsUtil.createCompte(rib, 1L)));
        given(virementRepository.findAllByRibEmetteurOrRibBeneficiaire(rib, rib))
                .willReturn(Collections.singletonList(createVirement(rib, null, null)));

        final List<Virement> virements = virementService.findAllForClient(1L);

        Assertions.assertNotNull(virements);
        Assertions.assertEquals(1, virements.size());
        verify(virementRepository, times(1)).findAllByRibEmetteurOrRibBeneficiaire(rib, rib);
        verify(compteService, times(1)).getComptesForClient(1L);
    }

    @Test
    public void findAllForClientNonExistantTest() {
        given(compteService.getComptesForClient(1L)).willReturn(Optional.empty());

        CompteNonExistantException exception = Assertions.assertThrows(CompteNonExistantException.class,
                () -> virementService.findAllForClient(1L));
    }

    @Test
    public void virementTest() {
        final String ribEmetteur = "007111111111111111111111";
        final String ribBeneficiaire = "007111111111111111111112";
        Virement expectedVirement = createVirement(ribEmetteur, ribBeneficiaire, BigDecimal.valueOf(10));
        Compte compteEmetteur = ModelsUtil.createCompte(ribEmetteur, 2L, BigDecimal.valueOf(100), null);
        Compte compteBeneficiaire = ModelsUtil.createCompte(ribBeneficiaire, 1L, BigDecimal.valueOf(100), null);
        given(compteService.findByRib(expectedVirement.getRibBeneficiaire())).willReturn(Optional.of(compteBeneficiaire));
        given(compteService.findByRib(expectedVirement.getRibEmetteur())).willReturn(Optional.of(compteEmetteur));
        given(compteService.save(any())).willReturn(null);
        given(virementRepository.save(any())).willReturn(null);
        doNothing().when(amqpSender).send(any());

        Virement actualVirement = virementService.virement(VirementMapper.map(expectedVirement));

        Assertions.assertEquals(BigDecimal.valueOf(10), actualVirement.getAmount());
        Assertions.assertEquals(ribEmetteur, actualVirement.getRibEmetteur());
        Assertions.assertEquals(ribBeneficiaire, actualVirement.getRibBeneficiaire());
        Assertions.assertEquals(ribBeneficiaire, actualVirement.getRibBeneficiaire());
        Assertions.assertEquals(BigDecimal.valueOf(90), compteEmetteur.getSolde());
        Assertions.assertEquals(BigDecimal.valueOf(110), compteBeneficiaire.getSolde());

        verify(compteService, times(2)).findByRib(anyString());
        verify(compteService, times(2)).save(any());
        verify(amqpSender, times(1)).send(any());
    }

    @ParameterizedTest
    @ValueSource(longs = {101, 500})
    public void virementWithSoldeInsuffisant(long montant) {
        final String ribEmetteur = "007111111111111111111111";
        final String ribBeneficiaire = "007111111111111111111112";
        Virement expectedVirement = createVirement(ribEmetteur, ribBeneficiaire, BigDecimal.valueOf(montant));
        Compte compteEmetteur = ModelsUtil.createCompte(ribEmetteur, 2L, BigDecimal.valueOf(100), null);
        given(compteService.findByRib(expectedVirement.getRibEmetteur())).willReturn(Optional.of(compteEmetteur));

        SoldeDisponibleInsuffisantException exception = Assertions.assertThrows(SoldeDisponibleInsuffisantException.class,
                () -> virementService.virement(VirementMapper.map(expectedVirement)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00711111111111111111111", "007111111111111111111113"})
    public void virementWithEmmeteurInexistant(String ribEmetteur) {
        final String ribBeneficiaire = "007111111111111111111112";
        Virement expectedVirement = createVirement(ribEmetteur, ribBeneficiaire, BigDecimal.valueOf(100));
        given(compteService.findByRib(expectedVirement.getRibEmetteur())).willReturn(Optional.empty());

        CompteNonExistantException exception = Assertions.assertThrows(CompteNonExistantException.class,
                () -> virementService.virement(VirementMapper.map(expectedVirement)));
    }

    @Test
    public void virementInterneSavedToBlockchain() {
        Virement expectedVirement = ModelsUtil.createVirement("007111111111111111111111", "007111111111111111111112", null);
        given(virementRepository.findByReference(VIREMENT_REF)).willReturn(Optional.of(expectedVirement));

        Virement actualVirement = virementService.saveVirementAddedToBlockchain(VIREMENT_REF);

        Assertions.assertEquals(VirementStatus.INTERNE_SAVED_IN_CORDA, actualVirement.getStatus());
        verify(virementRepository, times(1)).findByReference(VIREMENT_REF);
    }

    @Test
    public void virementExterneSavedToBlockchain() {
        Virement expectedVirement = ModelsUtil.createVirement("007111111111111111111111", "008111111111111111111112", null);
        given(virementRepository.findByReference(VIREMENT_REF)).willReturn(Optional.of(expectedVirement));
        given(virementRepository.save(any())).willReturn(null);

        Virement actualVirement = virementService.saveVirementAddedToBlockchain(VIREMENT_REF);

        Assertions.assertEquals(VirementStatus.EXTERNE_APPROVED, actualVirement.getStatus());
        verify(virementRepository, times(1)).findByReference(VIREMENT_REF);
    }

    @Test
    public void badReferenceVirementSavedToBlockchain() {
        given(virementRepository.findByReference(VIREMENT_REF)).willReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> virementService.saveVirementAddedToBlockchain(VIREMENT_REF));
        verify(virementRepository, times(1)).findByReference(VIREMENT_REF);
    }

    @ParameterizedTest
    @EnumSource(value = VirementStatus.class, names = {"EXTERNE_APPROVED", "EXTERNE_REJECTED"}, mode = EnumSource.Mode.INCLUDE)
    public void virementReceivedFromBlockchain(VirementStatus virementStatus) {
        BankTransferDTO dto = new BankTransferDTO("REF", "00811111111111111111111", "00711111111111111111111",
                BigDecimal.valueOf(100), Date.from(Instant.parse("2020-05-01T15:23:01Z")), virementStatus, null);
        given(virementRepository.save(any())).willReturn(null);

        Virement actualVirement = virementService.saveVirementReceivedFromBlockchain(dto);

        Assertions.assertEquals(virementStatus, actualVirement.getStatus());
        Assertions.assertEquals("00811111111111111111111", actualVirement.getRibEmetteur());
        Assertions.assertEquals("00711111111111111111111", actualVirement.getRibBeneficiaire());
        Assertions.assertEquals(BigDecimal.valueOf(100), actualVirement.getAmount());
        Assertions.assertEquals(Date.from(Instant.parse("2020-05-01T15:23:01Z")), actualVirement.getDateExecution());
        Assertions.assertNull(actualVirement.getMotif());
        verify(virementRepository, times(1)).save(any());
    }

    @ParameterizedTest
    @EnumSource(value = VirementStatus.class, names = {"EXTERNE_APPROVED", "EXTERNE_REJECTED"}, mode = EnumSource.Mode.EXCLUDE)
    public void badStatusVirementSavedToBlockchain(VirementStatus virementStatus) {
        BankTransferDTO dto = new BankTransferDTO("REF", "00811111111111111111111", "00711111111111111111111",
                BigDecimal.valueOf(100), Date.from(Instant.parse("2020-05-01T15:23:01Z")), virementStatus, null);

        IllegalReceivedVirementStatusException exception = Assertions.assertThrows(IllegalReceivedVirementStatusException.class,
                () -> virementService.saveVirementReceivedFromBlockchain(dto));
    }

}
