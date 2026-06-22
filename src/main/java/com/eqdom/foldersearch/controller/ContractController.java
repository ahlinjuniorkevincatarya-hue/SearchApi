package com.eqdom.foldersearch.controller;

import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    @PostMapping("/api/contrats/generate/{numDossier}")
    public ResponseEntity<ContratDto> createContract(@PathVariable String numDossier) throws Exception{

        return ResponseEntity.ok( contractService.generateContract(numDossier));
    }
}
