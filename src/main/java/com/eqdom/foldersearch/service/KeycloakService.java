package com.eqdom.foldersearch.service;

import com.eqdom.foldersearch.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KeycloakService {

    private final RestClient restClient;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    public KeycloakService(
            @Value("${keycloak.server-url}") String serverUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(serverUrl)
                .build();
    }

    public TokenResponse login(String username, String password) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("username", username);
        form.add("password", password);

        return restClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", realm)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(TokenResponse.class);
    }

    public TokenResponse refresh(String refreshToken) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("refresh_token", refreshToken);

        return restClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", realm)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(TokenResponse.class);
    }

}