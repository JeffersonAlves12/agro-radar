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

            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

            // Envia evento de login para o T2 por push
            pushService.enviarLoginEvento(usuario.getNome(), usuario.getEmail());

            // Retorna o AuthDTO com o token e os dados do usuário
            return ResponseEntity.ok(new AuthDTO(token, usuarioDTO));

        } catch (BadCredentialsException ex) {
            // Credenciais inválidas
            return ResponseEntity.status(401).build();
        }
    }

    // Classe auxiliar para receber o login
    public static class LoginRequest {
        private String email;
        private String senha;

        // Getters e Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
}
