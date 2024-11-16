package com.agro.radar.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sensor_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoSensor;

    private Double valor;

    private LocalDateTime timestamp;

    @JsonIgnore // Ignora o dispositivo na serialização JSON
    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

    @PrePersist
    public void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
