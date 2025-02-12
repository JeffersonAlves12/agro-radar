package com.agro.radar.dto;

import com.agro.radar.models.SensorData;
import java.time.LocalDateTime;

public class SensorDataDTO {
    private Long id;
    private String tipoSensor;
    private Double valor;
    private LocalDateTime timestamp;
    private Long dispositivoId;

    public SensorDataDTO(SensorData sensorData) {
        this.id = sensorData.getId();
        this.tipoSensor = sensorData.getTipoSensor();
        this.valor = sensorData.getValor();
        this.timestamp = sensorData.getTimestamp();
        this.dispositivoId = sensorData.getDispositivo() != null ? sensorData.getDispositivo().getId() : null;
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

    public Long getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(Long dispositivoId) {
        this.dispositivoId = dispositivoId;
    }
}