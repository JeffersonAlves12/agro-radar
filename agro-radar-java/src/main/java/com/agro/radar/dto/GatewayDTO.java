package com.agro.radar.dto;

import com.agro.radar.models.Gateway;
import java.util.Set;
import java.util.stream.Collectors;

public class GatewayDTO {
    private Long id;
    private String nome;
    private String localizacao;
    private Set<DispositivoDTO> dispositivos;

    public GatewayDTO(Gateway gateway) {
        this.id = gateway.getId();
        this.nome = gateway.getNome();
        this.localizacao = gateway.getLocalizacao();
        this.dispositivos = gateway.getDispositivos().stream()
            .map(DispositivoDTO::new)
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

    public Set<DispositivoDTO> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(Set<DispositivoDTO> dispositivos) {
        this.dispositivos = dispositivos;
    }
}