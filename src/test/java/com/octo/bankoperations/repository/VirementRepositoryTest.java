package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Virement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.octo.bankoperations.utils.ModelsUtil.createVirement;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VirementRepositoryTest {

    @Autowired
    private VirementRepository virementRepository;

    @BeforeAll
    public void setup() {
        List<Virement> virements = Arrays.asList(
                createVirement(4L, "REF1", BigDecimal.valueOf(10), new Date(), "motif",
                        "008111111111111111111112", "007111111111111111111000", null, null),
                createVirement(6L, "REF2", BigDecimal.valueOf(10), new Date(), "motif"
                        , "007111111111111111111000", "008111111111111111111113", null, null)
        );
        virementRepository.saveAll(virements);
    }

    @Test
    void findAllByRibEmetteurOrRibBeneficiaire_ReturnsListVirements() {
        List<Virement> actualResult = virementRepository
                .findVirementsByRibEmetteurOrRibBeneficiaire("008111111111111111111112", "008111111111111111111113");

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(2, actualResult.size());
        Assertions.assertTrue(actualResult.stream().anyMatch(vir -> vir.getRibEmetteur().equals("008111111111111111111112")));
        Assertions.assertTrue(actualResult.stream().anyMatch(vir -> vir.getRibBeneficiaire().equals("008111111111111111111113")));
    }

    @Test
    void findVirementByReference_ReturnsVirement() {

        Optional<Virement> actualResult = virementRepository.findByReference("REF2");

        Assertions.assertTrue(actualResult.isPresent());
        Assertions.assertEquals("REF2", actualResult.get().getReference());
    }

    @Test
    void findVirementByBadReference_ReturnsEmptyOptional() {

        Optional<Virement> actualResult = virementRepository.findByReference("REF3");

        Assertions.assertFalse(actualResult.isPresent());
    }
}