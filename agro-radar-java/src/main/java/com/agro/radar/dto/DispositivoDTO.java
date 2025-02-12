package com.agro.radar.dto;

import com.agro.radar.models.Dispositivo;
import java.util.Set;
import java.util.stream.Collectors;

public class DispositivoDTO {
    private Long id;
    private String nome;
    private String localizacao;
    private Long gatewayId;
    private Set<SensorDataDTO> sensores;

    public DispositivoDTO(Dispositivo dispositivo) {
        this.id = dispositivo.getId();
        this.nome = dispositivo.getNome();
        this.localizacao = dispositivo.getLocalizacao();
        this.gatewayId = dispositivo.getGateway() != null ? dispositivo.getGateway().getId() : null;
        this.sensores = dispositivo.getSensores().stream()
            .map(SensorDataDTO::new)
            .collect(Collectors.toSet());
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(Long gatewayId) {
        this.gatewayId = gatewayId;
    }

    public Set<SensorDataDTO> getSensores() {
        return sensores;
    }

    public void setSensores(Set<SensorDataDTO> sensores) {
        this.sensores = sensores;
    }
}