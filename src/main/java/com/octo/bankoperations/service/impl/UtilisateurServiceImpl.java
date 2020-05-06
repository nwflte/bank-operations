package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.domain.Adresse;
import com.octo.bankoperations.domain.Utilisateur;
import com.octo.bankoperations.dto.UtilisateurDTO;
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

    @Override
    public void save(UtilisateurDTO dto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setBirthdate(dto.getBirthdate());
        Adresse adresse = new Adresse();
        adresse.setAdresse1(dto.getAdresse1());
        adresse.setVille(dto.getVille());
        utilisateur.setAdresse(adresse);
        utilisateur.setFirstname(dto.getFirstname());
        utilisateur.setLastname(dto.getLastname());
        utilisateur.setGender(dto.getGender());
        utilisateur.setUsername(dto.getUsername());

        utilisateurRepository.save(utilisateur);
    }
}
