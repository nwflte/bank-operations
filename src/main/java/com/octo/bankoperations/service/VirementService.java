package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.VirementDTO;

import java.util.List;
import java.util.Optional;

public interface VirementService {
    List<Virement> loadAll();

    Optional<Virement> findById(Long id);

    List<Virement> findAllForUtilisateur(Long utilisateurId);

    void virement(VirementDTO virementDto);
}
