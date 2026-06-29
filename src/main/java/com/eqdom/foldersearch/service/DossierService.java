package com.eqdom.foldersearch.service;

import com.eqdom.foldersearch.dto.ClientDto;
import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.dto.DossierDto;
import com.eqdom.foldersearch.entity.Dossier;
import com.eqdom.foldersearch.exception.ResourceNotFoundException;
import com.eqdom.foldersearch.mapper.DossierMapper;
import com.eqdom.foldersearch.repository.DossierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DossierService {

    private final DossierRepository dossierRepository;
    private final DossierMapper dossierMapper;

    public DossierService(DossierRepository dossierRepository, DossierMapper dossierMapper) {
        this.dossierRepository = dossierRepository;
        this.dossierMapper = dossierMapper;
    }

    public DossierDto findByNumDossier(String numDossier) {

        Dossier dossier = dossierRepository.findByNumDossier(numDossier)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier "+numDossier+" introuvable"));

        return dossierMapper.toDto(dossier);
    }
}