package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.dto.ObligationRequestDTO;
import com.octo.bankoperations.dto.ObligationUpdateDTO;
import com.octo.bankoperations.exceptions.ObligationNotFoundException;
import com.octo.bankoperations.service.ObligationService;
import com.octo.bankoperations.service.StateStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping(value = "api/obligations") // The paths for HTTP requests are relative to this base path.
public class ObligationController {
    private static final Logger logger = LoggerFactory.getLogger(ObligationController.class);

    @Autowired
    private ObligationService obligationService;

    /**
     * Displays all ddr Obligations states that exist in the node's vault.
     */
    @GetMapping
    public ResponseEntity<List<CordaDDRObligationDTO>> getAllObligations() {
        return ResponseEntity.ok(obligationService.loadAll(StateStatus.ALL));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CordaDDRObligationDTO> getObligations(@PathVariable String id) {
        return ResponseEntity.ok(obligationService.findById(id).orElseThrow(() -> new ObligationNotFoundException(id)));
    }

    @GetMapping(value = "all-consumed")
    public ResponseEntity<List<CordaDDRObligationDTO>> getAllConsumedObligations() {
        return ResponseEntity.ok(obligationService.loadAll(StateStatus.CONSUMED));
    }

    @GetMapping(value = "all-unconsumed")
    public ResponseEntity<List<CordaDDRObligationDTO>> getAllUnconsumedObligations() {
        return ResponseEntity.ok(obligationService.loadAll(StateStatus.UNCONSUMED));
    }

    @PostMapping(value = "request-pledge", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> requestPledge(@RequestBody ObligationRequestDTO request) {
        String signedTx = obligationService.createPledge(request.getAmount()).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(signedTx);
    }

    @PostMapping("cancel-pledge")
    public ResponseEntity<String> cancelPledge(@RequestBody ObligationUpdateDTO request) {
        String signedTx = obligationService.cancelPledge(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping("deny-pledge")
    public ResponseEntity<String> denyPledge(@RequestBody ObligationUpdateDTO request){
        String signedTx = obligationService.denyPledge(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping("approve-pledge")
    public ResponseEntity<String> approvePledge(@RequestBody ObligationUpdateDTO request){
        String signedTx = obligationService.approvePledge(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping(value = "request-redeem")
    public ResponseEntity<String> requestRedeem(@RequestBody ObligationRequestDTO request) {
        String signedTx = obligationService.createRedeem(request.getAmount()).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(signedTx);
    }

    @PostMapping("cancel-redeem")
    public ResponseEntity<String> cancelRedeem(@RequestBody ObligationUpdateDTO request)  {
        String signedTx = obligationService.cancelRedeem(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping("deny-redeem")
    public ResponseEntity<String> denyRedeem(@RequestBody ObligationUpdateDTO request) {
        String signedTx = obligationService.denyRedeem(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

    @PostMapping("approve-redeem")
    public ResponseEntity<String> approveRedeem(@RequestBody ObligationUpdateDTO request) {
        String signedTx = obligationService.approveRedeem(request.getExternalId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(signedTx);
    }

}