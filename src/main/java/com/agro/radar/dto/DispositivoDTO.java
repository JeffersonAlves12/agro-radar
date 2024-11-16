package com.agro.radar.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.agro.radar.models.Dispositivo;

public class DispositivoDTO {

    private Long id;
    private String nome;
    private String localizacao;
    private Long gatewayId;
    private String gatewayNome;
    private List<SensorDataDTO> sensores;

    public DispositivoDTO(Dispositivo dispositivo) {
        this.id = dispositivo.getId();
        this.nome = dispositivo.getNome();
        this.localizacao = dispositivo.getLocalizacao();

        if (dispositivo.getGateway() != null) {
            this.gatewayId = dispositivo.getGateway().getId();
            this.gatewayNome = dispositivo.getGateway().getNome();
        }

        if (dispositivo.getSensores() != null) {
            this.sensores = dispositivo.getSensores().stream()
                .map(SensorDataDTO::new)
                .collect(Collectors.toList());
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalizacao() { return localizacao; }
    public Long getGatewayId() { return gatewayId; }
    public String getGatewayNome() { return gatewayNome; }
    public List<SensorDataDTO> getSensores() { return sensores; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public void setGatewayId(Long gatewayId) { this.gatewayId = gatewayId; }
    public void setGatewayNome(String gatewayNome) { this.gatewayNome = gatewayNome; }
    public void setSensores(List<SensorDataDTO> sensores) { this.sensores = sensores; }
}
