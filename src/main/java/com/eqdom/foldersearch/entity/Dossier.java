package com.eqdom.foldersearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.eqdom.foldersearch.Statut;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "dossier")
@NoArgsConstructor
@AllArgsConstructor
public class Dossier {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            name = "num_dossier",
            unique = true,
            nullable = false
    )
    private String numDossier;


    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;


    @ToString.Exclude
    @OneToMany(mappedBy = "dossier")
    private List<Contrat> contrats = new ArrayList<>();


    @Column(name = "montant")
    private Double montant;


    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}