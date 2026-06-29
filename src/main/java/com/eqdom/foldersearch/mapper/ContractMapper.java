package com.eqdom.foldersearch.mapper;

import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.entity.Contrat;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {
    public ContratDto toDto(Contrat contrat) {
        ContratDto contratDto = new ContratDto();

        contratDto.setReferenceContrat(contrat.getReferenceContrat());
        contratDto.setType(contrat.getType());
        contratDto.setStatut(contrat.getStatut().name());
        contratDto.setNumDossier(contrat.getDossier().getNumDossier());
        contratDto.setNomClient(contrat.getDossier().getClient().getNom()
        );

        return contratDto;
    }
}
