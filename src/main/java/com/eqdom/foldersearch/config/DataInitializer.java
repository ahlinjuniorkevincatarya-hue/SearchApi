package com.eqdom.foldersearch.config;

import com.eqdom.foldersearch.entity.Client;
import com.eqdom.foldersearch.entity.Dossier;
import com.eqdom.foldersearch.Statut;
import com.eqdom.foldersearch.repository.ClientRepository;
import com.eqdom.foldersearch.repository.DossierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final DossierRepository dossierRepository;

    public DataInitializer(ClientRepository clientRepository, DossierRepository dossierRepository) {
        this.clientRepository = clientRepository;
        this.dossierRepository = dossierRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // ── CLIENT 1 ──
        Client client1;
        if(clientRepository.existsByCni("BK789756")){
            client1 = clientRepository.findByCni("BK789756").orElseThrow();
        }else{
            client1 = new Client();
            client1.setCni("BK789756");
            client1.setNom("Amrani");
            client1.setPrenoms("Ahmed");
            client1.setTelephone("0612345678");
            client1.setAdresse("12 Rue des Fleurs, Casablanca");
            clientRepository.save(client1);

        }
        // ── DOSSIER 1 (client1) ──
        Dossier dossier1 ;
        if(dossierRepository.existsByNumDossier("EQ-2026-001")){
            dossier1 = dossierRepository.findByNumDossier("EQ-2026-001").orElseThrow();
        }else{
            dossier1 = new Dossier();
            dossier1.setNumDossier("EQ-2026-001");
            dossier1.setClient(client1);
            dossier1.setMontant(29000.0);
            dossier1.setStatut(Statut.VIDE);
            dossier1.setDateCreation(LocalDateTime.now());
            dossierRepository.save(dossier1);
        }

        // ── CLIENT 2 ──
        Client client2 ;
        if(clientRepository.existsByCni("CD789012")){
            client2 = clientRepository.findByCni("CD789012").orElseThrow();
        }else {
            client2 = new Client();
            client2.setCni("CD789012");
            client2.setNom("Benjelloun");
            client2.setPrenoms("Sanaa");
            client2.setTelephone("0698765432");
            client2.setAdresse("45 Avenue Hassan II, Rabat");
            clientRepository.save(client2);

        }

        // ── DOSSIER 2 (client2) ──
        Dossier dossier2 ;
        if(dossierRepository.existsByNumDossier("EQ-2026-002")){
            dossier2 = dossierRepository.findByNumDossier("EQ-2026-002").orElseThrow();
        }else{
            dossier2 = new Dossier();
            dossier2.setNumDossier("EQ-2026-002");
            dossier2.setClient(client2);
            dossier2.setMontant(120000.0);
            dossier2.setStatut(Statut.VIDE);
            dossier2.setDateCreation(LocalDateTime.now());
            dossierRepository.save(dossier2);
        }

        Client client3 ;
        if(clientRepository.existsByCni("BK123456")){
            client3 = clientRepository.findByCni("BK123456").orElseThrow();
        }else{
            client3 = new Client();
            client3.setCni("BK123456");
            client3.setNom("Bennani");
            client3.setPrenoms("Mohamed");
            client3.setTelephone("0698765432");
            client3.setAdresse("45 Avenue Hassan");
            clientRepository.save(client3);
        }

        Dossier dossier3;
        if(dossierRepository.existsByNumDossier("EQ-2026-003")){
            dossier3 = dossierRepository.findByNumDossier("EQ-2026-003").orElseThrow();
        }else {
            dossier3 = new Dossier();
            dossier3.setNumDossier("EQ-2026-003");
            dossier3.setClient(client3);
            dossier3.setMontant(28942.0);
            dossier3.setStatut(Statut.VIDE);
            dossier3.setDateCreation(LocalDateTime.now());
            dossierRepository.save(dossier3);
        }
        System.out.println("✅ Données mockées chargées avec succès !");
    }
}