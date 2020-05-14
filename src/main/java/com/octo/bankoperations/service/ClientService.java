package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<Client> getById(Long id);

    List<Client> getAll();

    Client save(ClientDTO dto);

    Client save(Client client);
}
