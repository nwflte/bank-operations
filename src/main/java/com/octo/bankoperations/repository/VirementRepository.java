package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirementRepository extends JpaRepository<Virement, Long> {
}
