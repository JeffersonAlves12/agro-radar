package com.agro.radar.dto;

import com.agro.radar.models.Usuario;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    // Por motivos de segurança, a senha não deve ser exposta.
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
}
