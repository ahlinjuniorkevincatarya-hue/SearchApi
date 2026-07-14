package com.eqdom.foldersearch.client;

import com.eqdom.foldersearch.dto.SignatureRequest;
import com.eqdom.foldersearch.dto.SignatureResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SignatureClient {

    private final RestClient restClient;

    public SignatureClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public SignatureResponse requestSignature(SignatureRequest request) {

        return restClient.post()
                .uri("/api/signatures")
                .body(request)
                .retrieve()
                .body(SignatureResponse.class);
    }

    public void afterSignature(String referenceContrat) {

        restClient.post()
                .uri("/api/signatures/afterSignature/" + referenceContrat)
                .retrieve()
                .toBodilessEntity();

    }

    public String getSignatureUrl(String referenceContrat) {

        return restClient.get()
                .uri("/api/signatures/signatureUrl/" + referenceContrat)
                .retrieve()
                .body(String.class);
    }
}