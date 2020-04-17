package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
  Optional<Compte> findByRib(String rib);
  boolean existsByRib(String rib);
}
