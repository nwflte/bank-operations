package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.domain.Utilisateur;
import com.octo.bankoperations.repository.UtilisateurRepository;
import com.octo.bankoperations.service.UtilisateurService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;


    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public Optional<Utilisateur> getById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }
}
