package com.eqdom.foldersearch.controller;

import com.eqdom.foldersearch.dto.LoginRequest;
import com.eqdom.foldersearch.dto.RefreshRequest;
import com.eqdom.foldersearch.dto.TokenResponse;
import com.eqdom.foldersearch.service.KeycloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final KeycloakService keycloakService;

    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request
    ) {

        TokenResponse response = keycloakService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @RequestBody RefreshRequest request
    ) {

        TokenResponse response = keycloakService.refresh(
                request.getRefreshToken()
        );

        return ResponseEntity.ok(response);
    }

}