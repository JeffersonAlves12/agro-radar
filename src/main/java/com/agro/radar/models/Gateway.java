package com.agro.radar.models;

import lombok.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "gateways")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String localizacao;

    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dispositivo> dispositivos;
}
