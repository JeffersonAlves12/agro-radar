package com.agro.radar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.radar.dto.UsuarioDTO;
import com.agro.radar.models.Usuario;
import com.agro.radar.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.buscarTodos()
                .stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioDTO(usuario);
    }

    @PostMapping
    public UsuarioDTO criar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return new UsuarioDTO(novoUsuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizar(id, usuarioAtualizado);
        return new UsuarioDTO(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}
