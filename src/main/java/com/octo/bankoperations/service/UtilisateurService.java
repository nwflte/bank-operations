package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    Optional<Utilisateur> getById(Long id);
    List<Utilisateur> getAll();
}
