package com.eqdom.foldersearch.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DossierDto {
    private String numDossier;
    private ClientDto client;
    private String statut;
    private Double montant;
    private LocalDateTime dateCreation;
    private List<ContratDto> contrats;
}
