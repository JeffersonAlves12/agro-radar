package com.agro.radar.dto;

public class AuthDTO {

    private String token;
    private UsuarioDTO usuario;

    // Construtor
    public AuthDTO(String token, UsuarioDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    // Getters e Setters
    public String getToken() { return token; }
    public UsuarioDTO getUsuario() { return usuario; }

    public void setToken(String token) { this.token = token; }
    public void setUsuario(UsuarioDTO usuario) { this.usuario = usuario; }
}
