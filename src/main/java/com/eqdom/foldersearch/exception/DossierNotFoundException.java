package com.eqdom.foldersearch.exception;

public class DossierNotFoundException extends RuntimeException {
    public DossierNotFoundException(String numDossier) {
        super("Dossier introuvable : " + numDossier);
    }
}