package com.eqdom.foldersearch.dto;

public class SignatureRequest {

    private String documentId;
    private String signataireEmail;
    private byte[] pdf;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getSignataireEmail() {
        return signataireEmail;
    }

    public void setSignataireEmail(String signataireEmail) {
        this.signataireEmail = signataireEmail;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
}