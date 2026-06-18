package com.eqdom.foldersearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "cni", unique = true)
    private String cni;


    @Column(name = "nom")
    private String nom;


    @Column(name = "prenoms")
    private String prenoms;


    @Column(name = "telephone")
    private String telephone;


    @Column(name = "adresse")
    private String adresse;


    @ToString.Exclude
    @OneToMany(mappedBy = "client")
    private List<Dossier> dossiers = new ArrayList<>();
}