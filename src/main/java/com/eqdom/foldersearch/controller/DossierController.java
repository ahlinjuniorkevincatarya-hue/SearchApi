package com.eqdom.foldersearch.controller;

import com.eqdom.foldersearch.dto.DossierDto;
import com.eqdom.foldersearch.service.DossierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/dossiers")
public class DossierController {
    private final DossierService dossierService;

    public DossierController(DossierService dossierService) {
        this.dossierService = dossierService;
    }

    @GetMapping("/{numDossier}")
    public ResponseEntity<DossierDto> rechercherDossier(@PathVariable String numDossier) {
        DossierDto dossier = dossierService.findByNumDossier(numDossier);
        return ResponseEntity.ok(dossier);
    }
}
