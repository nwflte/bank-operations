package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.dto.CompteDTO;
import com.octo.bankoperations.exceptions.ClientNotFoundException;
import com.octo.bankoperations.repository.ClientRepository;
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
    private final ClientRepository clientRepository;

    @Autowired
    public CompteServiceImpl(CompteRepository compteRepository, ClientRepository clientRepository) {
        this.compteRepository = compteRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Compte> getById(Long id) {
        return compteRepository.findById(id);
    }

    @Override
    public Optional<Compte> findByRib(String rib) {
        return compteRepository.findByRib(rib);
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
    public Optional<Compte> getComptesForClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return compteRepository.findByClient(client);
    }

    @Override
    public Compte save(CompteDTO dto) {
        Compte compte = new Compte();
        compte.setBlocked(dto.isBlocked());
        compte.setRib(dto.getRib());
        compte.setSolde(dto.getSolde());
        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new ClientNotFoundException(dto.getClientId())
        );
        compte.setClient(client);
        compte = compteRepository.save(compte);
        client.setCompte(compte);
        clientRepository.save(client);
        return compte;
    }

    @Override
    public Compte save(Compte compte) {
        return compteRepository.save(compte);
    }
}
