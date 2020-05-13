package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.dto.CompteDTO;

import java.util.List;
import java.util.Optional;

public interface CompteService {
    Optional<Compte> getById(Long id);

    Optional<Compte> findByRib(String rib);

    List<Compte> getAll();

    boolean existsByRIB(String rib);

    Optional<Compte> getComptesForClient(Long id);

    Compte save(CompteDTO dto);
}
