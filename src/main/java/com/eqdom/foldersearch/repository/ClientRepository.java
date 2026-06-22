package com.eqdom.foldersearch.repository;

import com.eqdom.foldersearch.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByCni(String cni);
    Optional<Client> findByCni(String cni);
}
