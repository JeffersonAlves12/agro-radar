package com.agro.radar.push;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PushEvent {
    private String tipo;                // Tipo do evento (ex.: "login")
    private String mensagem;            // Mensagem explicativa
    private String timestamp;           // Data e hora do evento como string no formato ISO 8601
    private Map<String, Object> detalhes; // Detalhes específicos do evento

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public PushEvent(String tipo, String mensagem, LocalDateTime timestamp, Map<String, Object> detalhes) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.timestamp = timestamp == null ? LocalDateTime.now().format(FORMATTER) : timestamp.format(FORMATTER);
        this.detalhes = detalhes;
    }

    // Getters e setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(Map<String, Object> detalhes) {
        this.detalhes = detalhes;
    }
}
