package com.agro.radar.dto;

import java.time.LocalDateTime;

import com.agro.radar.models.SensorData;

public class SensorDataDTO {

    private Long id;
    private String tipoSensor;
    private Double valor;
    private LocalDateTime timestamp;
    private String dispositivoNome; // Nome do dispositivo associado ao sensor

    // Construtor baseado em SensorData
    public SensorDataDTO(SensorData sensorData) {
        this.id = sensorData.getId();
        this.tipoSensor = sensorData.getTipoSensor();
        this.valor = sensorData.getValor();
        this.timestamp = sensorData.getTimestamp();
        this.dispositivoNome = sensorData.getDispositivo() != null ? sensorData.getDispositivo().getNome() : null;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDispositivoNome() {
        return dispositivoNome;
    }

    public void setDispositivoNome(String dispositivoNome) {
        this.dispositivoNome = dispositivoNome;
    }
}
