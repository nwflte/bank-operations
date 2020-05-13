package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.enums.StateStatus;
import com.octo.bankoperations.exceptions.ObligationNotFoundException;
import com.octo.bankoperations.service.ObligationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping(value = "api/obligations") // The paths for HTTP requests are relative to this base path.
public class ObligationController {
    private static final Logger logger = LoggerFactory.getLogger(ObligationController.class);

    @Autowired
    private ObligationService obligationService;

    @GetMapping
    public ResponseEntity<List<CordaDDRObligationDTO>> getAll() {
        return ResponseEntity.ok(obligationService.loadAllObligations(StateStatus.ALL));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CordaDDRObligationDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(obligationService.findObligationByExternalId(id).orElseThrow(() -> new ObligationNotFoundException(id)));
    }

}