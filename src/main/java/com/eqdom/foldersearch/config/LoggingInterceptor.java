package com.eqdom.foldersearch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        long start = System.currentTimeMillis();

        log.info("========== HTTP REQUEST ==========");
        log.info("Method : {}", request.getMethod());
        log.info("URL    : {}", request.getURI());
        log.info("Headers:\n{}", request.getHeaders());

        if (body.length > 0) {
            log.info("Body:\n{}", new String(body, StandardCharsets.UTF_8));
        }

        ClientHttpResponse response = execution.execute(request, body);

        long duration = System.currentTimeMillis() - start;

        byte[] responseBody = response.getBody().readAllBytes();

        log.info("========== HTTP RESPONSE ==========");
        log.info("Status : {}", response.getStatusCode());
        log.info("Headers:\n{}", response.getHeaders());

        if (responseBody.length > 0) {
            log.info("Body:\n{}", new String(responseBody, StandardCharsets.UTF_8));
        }

        log.info("Duration : {} ms", duration);

        return new BufferingClientHttpResponseWrapper(response, responseBody);
    }

    /**
     * Permet de relire le body après l'avoir loggé.
     */
    static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse response;
        private final byte[] body;

        BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
            this.response = response;
            this.body = body;
        }

        @Override
        public org.springframework.http.HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }

        @Override
        public java.io.InputStream getBody() {
            return new ByteArrayInputStream(body);
        }

        @Override
        public org.springframework.http.HttpHeaders getHeaders() {
            return response.getHeaders();
        }
    }
}