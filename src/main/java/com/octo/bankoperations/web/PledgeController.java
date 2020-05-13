package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.dto.ObligationRequestDTO;
import com.octo.bankoperations.dto.ObligationUpdateDTO;
import com.octo.bankoperations.enums.StateStatus;
import com.octo.bankoperations.exceptions.ObligationNotFoundException;
import com.octo.bankoperations.service.ObligationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/obligations/pledges")
public class PledgeController {

    @Autowired
    private ObligationService obligationService;

    @GetMapping(value = "{id}")
    public ResponseEntity<CordaDDRObligationDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(obligationService.findPledgeByExternalId(id).orElseThrow(() -> new ObligationNotFoundException(id)));
    }

    @GetMapping
    public ResponseEntity<List<CordaDDRObligationDTO>> getAll(@RequestParam(value = "status", defaultValue = "all") String status ) {
        return ResponseEntity.ok(obligationService.loadAllPledges(StateStatus.valueOf(status.toUpperCase())));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> request(@RequestBody ObligationRequestDTO request) {
        String signedTx = obligationService.createPledge(request.getAmount()).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(signedTx);
    }

    @PostMapping("cancel")
    public ResponseEntity<String> cancel(@RequestBody ObligationUpdateDTO request) {
        String signedTx = obligationService.cancelPledge(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping("deny")
    public ResponseEntity<String> deny(@RequestBody ObligationUpdateDTO request){
        String signedTx = obligationService.denyPledge(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping("approve")
    public ResponseEntity<String> approve(@RequestBody ObligationUpdateDTO request){
        String signedTx = obligationService.approvePledge(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }
}
