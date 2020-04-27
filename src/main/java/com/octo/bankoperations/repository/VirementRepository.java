package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirementRepository extends JpaRepository<Virement, Long> {
    List<Virement> findAllByCompteEmetteurOrRibBeneficiaire(Compte compteEmetteur, String ribBeneficaire);
}
