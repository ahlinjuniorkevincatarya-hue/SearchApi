package com.eqdom.foldersearch.dto;

import lombok.Data;

@Data
public class SignatureRequest {

    private String documentId;
    private String signataireEmail;
    private byte[] pdf;
}