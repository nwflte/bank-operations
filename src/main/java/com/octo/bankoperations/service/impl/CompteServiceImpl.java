package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    @Autowired
    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public boolean existsByRIB(String rib) {
        return compteRepository.existsByRib(rib);
    }
}
