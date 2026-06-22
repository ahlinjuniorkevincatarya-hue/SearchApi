package com.eqdom.foldersearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.eqdom.foldersearch.Statut;


@Entity
@Data
@Table(name = "contrats")
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            name = "num_contrat",
            unique = true
    )
    private String numContrat;


    @Column(name = "type")
    private String type;


    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;


    @ManyToOne
    @JoinColumn(name = "dossier_id", nullable = false)
    private Dossier dossier;

    @Column(name = "document_original")
    private String documentOriginal;

    @Column(name = "document_signe")
    private String documentSigne;
}