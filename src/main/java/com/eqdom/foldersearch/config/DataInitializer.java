package com.eqdom.foldersearch.config;

import com.eqdom.foldersearch.entity.Client;
import com.eqdom.foldersearch.entity.Contrat;
import com.eqdom.foldersearch.entity.Dossier;
import com.eqdom.foldersearch.Statut;
import com.eqdom.foldersearch.repository.ClientRepository;
import com.eqdom.foldersearch.repository.ContratRepository;
import com.eqdom.foldersearch.repository.DossierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final DossierRepository dossierRepository;
    private final ContratRepository contratRepository;

    public DataInitializer(ClientRepository clientRepository, DossierRepository dossierRepository, ContratRepository contratRepository) {
        this.clientRepository = clientRepository;
        this.dossierRepository = dossierRepository;
        this.contratRepository = contratRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // ── CLIENT 1 ──
        Client client1 = new Client();
        client1.setCni("BK789756");
        client1.setNom("Amrani");
        client1.setPrenoms("Ahmed");
        client1.setTelephone("0612345678");
        client1.setAdresse("12 Rue des Fleurs, Casablanca");
        clientRepository.save(client1);

        // ── DOSSIER 1 (client1) ──
        Dossier dossier1 = new Dossier();
        dossier1.setNumDossier("EQ-2026-001");
        dossier1.setClient(client1);
        dossier1.setMontant(29000.0);
        dossier1.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        dossier1.setDateCreation(LocalDateTime.now());
        dossierRepository.save(dossier1);

        // ── CONTRATS du DOSSIER 1 ──
        Contrat contrat1 = new Contrat();
        contrat1.setNumContrat("C001");
        contrat1.setType("Contrat de crédit");
        contrat1.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat1.setDossier(dossier1);
        contratRepository.save(contrat1);

        Contrat contrat2 = new Contrat();
        contrat2.setNumContrat("C002");
        contrat2.setType("Mandat de prélèvement");
        contrat2.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat2.setDossier(dossier1);
        contratRepository.save(contrat2);

        Contrat contrat3 = new Contrat();
        contrat3.setNumContrat("C003");
        contrat3.setType("Fiche précontractuelle");
        contrat3.setStatut(Statut.SIGNE);
        contrat3.setDossier(dossier1);
        contratRepository.save(contrat2);

        // ── CLIENT 2 ──
        Client client2 = new Client();
        client2.setCni("CD789012");
        client2.setNom("Benjelloun");
        client2.setPrenoms("Sanaa");
        client2.setTelephone("0698765432");
        client2.setAdresse("45 Avenue Hassan II, Rabat");
        clientRepository.save(client2);

        // ── DOSSIER 2 (client2) ──
        Dossier dossier2 = new Dossier();
        dossier2.setNumDossier("EQ-2026-002");
        dossier2.setClient(client2);
        dossier2.setMontant(120000.0);
        dossier2.setStatut(Statut.SIGNE);
        dossier2.setDateCreation(LocalDateTime.now());
        dossierRepository.save(dossier2);

        // ── CONTRATS du DOSSIER 2 ──
        Contrat contrat4 = new Contrat();
        contrat4.setNumContrat("C004");
        contrat4.setType("Contrat de credit");
        contrat4.setStatut(Statut.SIGNE);
        contrat4.setDossier(dossier2);
        contratRepository.save(contrat4);

        Contrat contrat5 = new Contrat();
        contrat5.setNumContrat("C005");
        contrat5.setType("Assurance de décès");
        contrat5.setStatut(Statut.SIGNE);
        contrat5.setDossier(dossier2);
        contratRepository.save(contrat5);

        Contrat contrat6 = new Contrat();
        contrat6.setNumContrat("C006");
        contrat6.setType("Mandat de prevelement");
        contrat6.setStatut(Statut.SIGNE);
        contrat6.setDossier(dossier2);
        contratRepository.save(contrat6);

        Contrat contrat7 = new Contrat();
        contrat7.setNumContrat("C007");
        contrat7.setType("Fiche précontractuelle");
        contrat7.setStatut(Statut.SIGNE);
        contrat7.setDossier(dossier2);
        contratRepository.save(contrat7);

        Client client3 = new Client();
        client3.setCni("BK123456");
        client3.setNom("Bennani");
        client3.setPrenoms("Mohamed");
        client3.setTelephone("0698765432");
        client3.setAdresse("45 Avenue Hassan");
        clientRepository.save(client3);

        Dossier dossier3 = new Dossier();
        dossier3.setNumDossier("EQ-2026-003");
        dossier3.setClient(client3);
        dossier3.setMontant(28942.0);
        dossier3.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        dossier3.setDateCreation(LocalDateTime.now());
        dossierRepository.save(dossier3);

        Contrat contrat8 = new Contrat();
        contrat8.setNumContrat("C008");
        contrat8.setType("contrat de credit");
        contrat8.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat8.setDossier(dossier3);
        contratRepository.save(contrat8);

        Contrat contrat9 = new Contrat();
        contrat9.setNumContrat("C009");
        contrat9.setType("Assurance de deces");
        contrat9.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat9.setDossier(dossier3);
        contratRepository.save(contrat9);

        System.out.println("✅ Données mockées chargées avec succès !");
    }
}