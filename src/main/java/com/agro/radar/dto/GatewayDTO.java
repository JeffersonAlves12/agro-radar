package com.agro.radar.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.agro.radar.models.Gateway;

public class GatewayDTO {

    private Long id;
    private String nome;
    private String localizacao;
    private List<DispositivoDTO> dispositivos;

    // Construtor baseado em Gateway
    public GatewayDTO(Gateway gateway) {
        this.id = gateway.getId();
        this.nome = gateway.getNome();
        this.localizacao = gateway.getLocalizacao();

        if (gateway.getDispositivos() != null) {
            this.dispositivos = gateway.getDispositivos().stream()
                .map(DispositivoDTO::new)
                .collect(Collectors.toList());
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalizacao() { return localizacao; }
    public List<DispositivoDTO> getDispositivos() { return dispositivos; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public void setDispositivos(List<DispositivoDTO> dispositivos) { this.dispositivos = dispositivos; }
}
