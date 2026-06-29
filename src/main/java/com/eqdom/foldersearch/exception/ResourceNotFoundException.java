package com.eqdom.foldersearch.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String numDossier) {
        super("Dossier introuvable : " + numDossier);
    }
}