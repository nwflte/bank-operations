package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.CompteDTO;
import com.octo.bankoperations.exceptions.CompteNonExistantException;
import com.octo.bankoperations.mapper.CompteMapper;
import com.octo.bankoperations.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/comptes")
public class CompteController {

    private final CompteService compteService;

    @Autowired
    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping("/exists/{rib}")
    public ResponseEntity<Boolean> ribExists(@PathVariable String rib){
        return ResponseEntity.ok(compteService.existsByRIB(rib));
    }

    @GetMapping("{id}")
    public ResponseEntity<CompteDTO> getById(@PathVariable Long id){
        return ResponseEntity.
                ok(CompteMapper.map((compteService.getById(id).orElseThrow(() -> new CompteNonExistantException(id)))));
    }

    @GetMapping("utilisateur/{id}")
    public ResponseEntity<CompteDTO> getByUtilisateurId(@PathVariable Long id){
        return ResponseEntity.
                ok(CompteMapper.map(compteService.getForUtilisateur(id).orElseThrow(CompteNonExistantException::new)));
    }
}
