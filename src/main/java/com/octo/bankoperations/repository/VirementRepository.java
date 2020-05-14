package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VirementRepository extends JpaRepository<Virement, Long> {
    List<Virement> findVirementsByRibEmetteurOrRibBeneficiaire(String ribEmetteur, String ribBeneficaire);

    Optional<Virement> findByReference(String reference);
}
