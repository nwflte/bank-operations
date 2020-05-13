package com.octo.bankoperations.service.impl;

import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.dto.ClientDTO;
import com.octo.bankoperations.mapper.ClientMapper;
import com.octo.bankoperations.repository.ClientRepository;
import com.octo.bankoperations.service.ClientService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(ClientDTO dto) {
        Client client = ClientMapper.map(dto);
        clientRepository.save(client);
        return client;
    }
}
