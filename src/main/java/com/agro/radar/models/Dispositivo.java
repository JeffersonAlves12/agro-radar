package com.agro.radar.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dispositivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private Gateway gateway;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SensorData> sensores;
}
