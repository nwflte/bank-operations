package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.ClientDTO;
import com.octo.bankoperations.dto.CompteDTO;
import com.octo.bankoperations.dto.VirementDTO;
import com.octo.bankoperations.exceptions.ClientNotFoundException;
import com.octo.bankoperations.mapper.ClientMapper;
import com.octo.bankoperations.mapper.CompteMapper;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.service.ClientService;
import com.octo.bankoperations.service.VirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    private final ClientService clientService;
    private final VirementService virementService;

    public ClientController(ClientService clientService, VirementService virementService) {
        this.clientService = clientService;
        this.virementService = virementService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        return ResponseEntity.ok(clientService.getAll().stream().map(ClientMapper::map).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ClientMapper
                .map(clientService.getById(id).orElseThrow(() -> new ClientNotFoundException(id))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO dto) {
        return new ResponseEntity<>(ClientMapper.map(clientService.save(dto)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/compte")
    public ResponseEntity<CompteDTO> getCompte(@PathVariable Long id) {
        return ResponseEntity.ok(CompteMapper
                .map(clientService.getById(id).orElseThrow(() -> new ClientNotFoundException(id))
                        .getCompte()));
    }

    @GetMapping("/{id}/virements")
    public ResponseEntity<List<VirementDTO>> getVirements(@PathVariable Long id) {
        return ResponseEntity.ok(virementService
                .findAllForClient(id).stream().map(VirementMapper::map).collect(Collectors.toList()));
    }

}
