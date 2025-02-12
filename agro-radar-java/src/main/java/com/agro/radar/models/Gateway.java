package com.agro.radar.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gateways")
public class Gateway {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    private String localizacao;
    
    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Dispositivo> dispositivos = new HashSet<>();

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

    public Set<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(Set<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }
}