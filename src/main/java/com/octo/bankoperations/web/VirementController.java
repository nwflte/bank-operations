package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.dto.VirementDTO;
import com.octo.bankoperations.mapper.VirementMapper;
import com.octo.bankoperations.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/virements")
class VirementController {

    @Autowired
    private VirementService virementService;

    @GetMapping
    public List<VirementDTO> loadAll() {
        return Optional.ofNullable(virementService.loadAll()).orElse(Collections.emptyList())
                .stream().map(VirementMapper::map).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VirementDTO createTransaction(@Valid @RequestBody VirementDTO virementDto) {
        return VirementMapper.map(virementService.virement(virementDto));
    }

    @GetMapping("/{id}")
    public VirementDTO getById(@PathVariable Long id) {
        return VirementMapper.map(virementService.findById(id).orElseThrow(() -> new IllegalArgumentException("")));
    }

}
