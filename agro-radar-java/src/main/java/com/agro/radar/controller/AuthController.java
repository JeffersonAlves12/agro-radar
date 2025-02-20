package com.agro.radar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.radar.dto.AuthDTO;
import com.agro.radar.dto.UsuarioDTO;
import com.agro.radar.models.Usuario;
import com.agro.radar.push.PushService;
import com.agro.radar.security.JwtTokenProvider;
import com.agro.radar.services.MessageService;
import com.agro.radar.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PushService pushService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> autenticar(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            String email = authentication.getName();
            String token = jwtTokenProvider.gerarToken(email);

            // Armazena o token no PushService
            pushService.armazenarToken(token);

            // Busca o usuário completo
            Usuario usuario = usuarioService.buscarPorEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            // Enviar mensagem para enviar e-mail
            messageService.sendEmailMessage(
                "Login Realizado",
                "jafelix495@gmail.com",
                String.format("O usuário %s (%s) fez login com sucesso.", usuario.getNome(), usuario.getEmail())
            );

            // Envia evento de login para o T2 por push
            pushService.enviarLoginEvento(usuario.getNome(), usuario.getEmail());

            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
            return ResponseEntity.ok(new AuthDTO(token, usuarioDTO));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    public static class LoginRequest {
        private String email;
        private String senha;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
}