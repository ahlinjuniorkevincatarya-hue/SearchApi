package com.eqdom.foldersearch.dto;

import com.eqdom.foldersearch.ContractType;
import lombok.Data;

@Data
public class ContratDto {
    private String referenceContrat;
    private ContractType type;
    private String statut;

    private String numDossier;
    private String nomClient;


}
