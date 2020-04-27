package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    @Autowired
    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Optional<Compte> getById(Long id) {
        return compteRepository.findById(id);
    }

    @Override
    public List<Compte> getAll() {
        return compteRepository.findAll();
    }

    @Override
    public boolean existsByRIB(String rib) {
        return compteRepository.existsByRib(rib);
    }

    @Override
    public Optional<Compte> getForUtilisateur(Long id) {
        return compteRepository.findByUtilisateur_Id(id);
    }
}
