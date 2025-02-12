package com.agro.radar.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Importações atualizadas
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Importante para API stateless
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.agro.radar.security.JwtAuthenticationFilter;
import com.agro.radar.services.UsuarioService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro de Autenticação JWT

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable())
        .cors(withDefaults())
        .authorizeHttpRequests(requests -> requests
            // .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
            // .requestMatchers("/api/gateways/**").authenticated() // Somente os endpoints de gateways requerem autenticação
            .anyRequest().permitAll() // Outros endpoints são públicos por padrão
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuração de CORS

    // Used by Spring Security if CORS is enabled.
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
