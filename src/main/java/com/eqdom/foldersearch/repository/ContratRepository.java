package com.eqdom.foldersearch.repository;

import com.eqdom.foldersearch.ContractType;
import com.eqdom.foldersearch.entity.Contrat;
import com.eqdom.foldersearch.entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    boolean existsByDossierAndType(Dossier dossier, ContractType type);
    Optional<Contrat> findByReferenceContrat(String referenceContrat);
}
