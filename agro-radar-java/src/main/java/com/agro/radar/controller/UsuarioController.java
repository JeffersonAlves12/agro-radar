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
import com.agro.radar.push.PushService;
import com.agro.radar.services.MessageService;
import com.agro.radar.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PushService pushService;

    @Autowired
    private MessageService messageService;

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

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Novo Usuário Criado",
            "jafelix495@gmail.com",
            String.format("O usuário %s (%s) foi criado com sucesso.", novoUsuario.getNome(), novoUsuario.getEmail())
        );

        // Push para criação de usuário
        pushService.enviarUsuarioCriadoEvento(novoUsuario.getNome(), novoUsuario.getEmail());

        return new UsuarioDTO(novoUsuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizar(id, usuarioAtualizado);

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Usuário Atualizado",
            "jafelix495@gmail.com",
            String.format("O usuário %s (%s) foi atualizado com sucesso.", usuario.getNome(), usuario.getEmail())
        );

        // Push para atualização de usuário
        pushService.enviarUsuarioAtualizadoEvento(usuario.getNome(), usuario.getEmail());

        return new UsuarioDTO(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Usuário Excluído",
            "jafelix495@gmail.com",
            String.format("O usuário %s (%s) foi excluído com sucesso.", usuario.getNome(), usuario.getEmail())
        );

        // Push para exclusão de usuário
        pushService.enviarUsuarioExcluidoEvento(usuario.getNome(), usuario.getEmail());

        usuarioService.deletar(id);
    }
}