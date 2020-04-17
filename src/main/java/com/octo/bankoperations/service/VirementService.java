package com.octo.bankoperations.service;

import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.VirementDto;

import java.util.List;
import java.util.Optional;

public interface VirementService {
    List<Virement> loadAll();

    Optional<Virement> findById(Long id);

    void virement(VirementDto virementDto);
}
