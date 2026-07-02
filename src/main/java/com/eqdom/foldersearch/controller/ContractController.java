package com.eqdom.foldersearch.controller;

import com.eqdom.foldersearch.ContractType;
import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.dto.SignatureResponse;
import com.eqdom.foldersearch.service.ContratService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/contrats")
public class ContractController {

    private final ContratService contractService;

    public ContractController(ContratService contractService){
        this.contractService = contractService;
    }

    @PostMapping("/generate/{numDossier}/{type}")
    public ResponseEntity<ContratDto> createContract(@PathVariable String numDossier, @PathVariable ContractType type) throws Exception{

        return ResponseEntity.ok( contractService.generateContract(numDossier, type));
    }

    @GetMapping("/{referenceContrat}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable String referenceContrat) throws Exception{
        return ResponseEntity.ok()
                .header("content-Type","application/pdf")
                .body(contractService.getPdf(referenceContrat));
    }

    @PostMapping("/{referenceContrat}/signature")
    public SignatureResponse signer(@PathVariable String referenceContrat)
            throws Exception {

        return contractService.requestSignature(referenceContrat);
    }
}
