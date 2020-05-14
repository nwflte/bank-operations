package com.octo.bankoperations.mapper;

import com.octo.bankoperations.domain.Adresse;
import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.dto.ClientDTO;

public class ClientMapper {

    private ClientMapper() {
    }

    public static ClientDTO map(Client client) {
        if (client == null) return null;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setFirstname(client.getFirstname());
        clientDTO.setLastname(client.getLastname());
        clientDTO.setBirthdate(client.getBirthdate());
        clientDTO.setAdresse1(client.getAdresse().getAdresse1());
        clientDTO.setAdresse2(client.getAdresse().getAdresse2());
        clientDTO.setVille(client.getAdresse().getVille());
        clientDTO.setGender(client.getGender());
        clientDTO.setUsername(client.getUsername());
        clientDTO.setEmail(client.getEmail());
        return clientDTO;
    }

    public static Client map(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        Adresse adresse = new Adresse();
        adresse.setAdresse1(dto.getAdresse1());
        adresse.setAdresse2(dto.getAdresse2());
        adresse.setVille(dto.getVille());
        client.setAdresse(adresse);
        client.setBirthdate(dto.getBirthdate());
        client.setFirstname(dto.getFirstname());
        client.setEmail(dto.getEmail());
        client.setGender(dto.getGender());
        client.setLastname(dto.getLastname());
        client.setUsername(dto.getUsername());
        return client;
    }
}
