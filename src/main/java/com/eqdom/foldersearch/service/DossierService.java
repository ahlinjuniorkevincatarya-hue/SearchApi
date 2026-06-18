package com.eqdom.foldersearch.service;

import com.eqdom.foldersearch.dto.ClientDto;
import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.dto.DossierDto;
import com.eqdom.foldersearch.entity.Dossier;
import com.eqdom.foldersearch.exception.DossierNotFoundException;
import com.eqdom.foldersearch.repository.DossierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DossierService {

    private final DossierRepository dossierRepository;

    public DossierService(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    public DossierDto findByNumDossier(String numDossier) {
        Dossier dossier = dossierRepository.findByNumDossier(numDossier)
                .orElseThrow(() -> new DossierNotFoundException(numDossier));
        return convertToDTO(dossier);
    }

    private DossierDto convertToDTO(Dossier dossier) {
        DossierDto dto = new DossierDto();
        dto.setNumDossier(dossier.getNumDossier());
        dto.setStatut(dossier.getStatut().name());
        dto.setMontant(dossier.getMontant());
        dto.setDateCreation(dossier.getDateCreation());

        // Client
        ClientDto clientDTO = new ClientDto();
        clientDTO.setCni(dossier.getClient().getCni());
        clientDTO.setNom(dossier.getClient().getNom());
        clientDTO.setPrenoms(dossier.getClient().getPrenoms());
        clientDTO.setTelephone(dossier.getClient().getTelephone());
        clientDTO.setAdresse(dossier.getClient().getAdresse());
        dto.setClient(clientDTO);

        // Contrats
        List<ContratDto> contratsDTO = dossier.getContrats().stream()
                .map(contrat -> {
                    ContratDto contratDTO = new ContratDto();
                    contratDTO.setNumContrat(contrat.getNumContrat());
                    contratDTO.setType(contrat.getType());
                    contratDTO.setStatut(contrat.getStatut().name());
                    return contratDTO;
                })
                .toList();
        dto.setContrats(contratsDTO);

        return dto;
    }
}