package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Compte;

import java.util.List;
import java.util.Optional;

public interface CompteService {
    Optional<Compte> getById(Long id);
    List<Compte> getAll();
    boolean existsByRIB(String rib);
    Optional<Compte> getForUtilisateur(Long id);
}
