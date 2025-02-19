package com.agro.radar.push;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PushClient {
    private final RestTemplate restTemplate;

    public PushClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarEvento(String url, PushEvent evento, String token) {
        try {
            // Configura os cabeçalhos da requisição
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token); // Adiciona o token no cabeçalho
            headers.set("Content-Type", "application/json");

            // Cria a entidade da requisição (corpo + cabeçalho)
            HttpEntity<PushEvent> request = new HttpEntity<>(evento, headers);

            // Envia a requisição para o T2
            ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);

            System.out.println("Evento enviado com sucesso ao T2: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Erro ao enviar evento para o T2: " + e.getMessage());
        }
    }
}
