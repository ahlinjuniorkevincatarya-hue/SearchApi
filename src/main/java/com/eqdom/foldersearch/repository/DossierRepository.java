package com.eqdom.foldersearch.repository;

import com.eqdom.foldersearch.entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    Optional<Dossier> findByNumDossier(String numDossier);
}
