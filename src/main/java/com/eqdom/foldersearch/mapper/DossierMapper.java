package com.eqdom.foldersearch.mapper;

import com.eqdom.foldersearch.dto.DossierDto;
import com.eqdom.foldersearch.entity.Dossier;
import org.springframework.stereotype.Component;

@Component
public class DossierMapper {
    private final ClientMapper clientMapper;
    private final ContractMapper contractMapper;

    public DossierMapper(ClientMapper clientMapper, ContractMapper contractMapper) {
        this.clientMapper = clientMapper;
        this.contractMapper = contractMapper;
    }

    public DossierDto toDto(Dossier dossier){
        DossierDto dossierDto = new DossierDto();

        dossierDto.setNumDossier(dossier.getNumDossier());
        dossierDto.setStatut(dossier.getStatut().name());
        dossierDto.setMontant(dossier.getMontant());
        dossierDto.setDateCreation(dossier.getDateCreation());
        dossierDto.setClient(clientMapper.toDto(dossier.getClient()));
        dossierDto.setContrats(dossier.getContrats().stream().map(contractMapper::toDto).toList());

    return dossierDto;
    }
}
