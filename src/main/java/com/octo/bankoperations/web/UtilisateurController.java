package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.CompteDTO;
import com.octo.bankoperations.dto.UtilisateurDTO;
import com.octo.bankoperations.dto.VirementDTO;
import com.octo.bankoperations.exceptions.UtilisateurNotFoundException;
import com.octo.bankoperations.mapper.CompteMapper;
import com.octo.bankoperations.mapper.UtilisateurMapper;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.service.UtilisateurService;
import com.octo.bankoperations.service.VirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final VirementService virementService;

    public UtilisateurController(UtilisateurService utilisateurService, VirementService virementService) {
        this.utilisateurService = utilisateurService;
        this.virementService = virementService;
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAll(){
        return ResponseEntity.ok(utilisateurService.getAll().stream().map(UtilisateurMapper::map).collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody UtilisateurDTO dto){
        utilisateurService.save(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(UtilisateurMapper
                .map(utilisateurService.getById(id).orElseThrow(() -> new UtilisateurNotFoundException(id))));
    }

    @GetMapping("/{id}/compte")
    public ResponseEntity<CompteDTO> getCompte(@PathVariable Long id){
        return ResponseEntity.ok(CompteMapper
                .map(utilisateurService.getById(id).orElseThrow(() -> new UtilisateurNotFoundException(id))
                .getCompte()));
    }

    @GetMapping("/{id}/virements")
    public ResponseEntity<List<VirementDTO>> getVirements(@PathVariable Long id){
        return ResponseEntity.ok(virementService
                .findAllForUtilisateur(id).stream().map(VirementMapper::map).collect(Collectors.toList()));
    }

}
