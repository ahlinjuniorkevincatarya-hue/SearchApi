package com.eqdom.foldersearch.repository;

import com.eqdom.foldersearch.entity.Client;
import com.eqdom.foldersearch.entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
}
