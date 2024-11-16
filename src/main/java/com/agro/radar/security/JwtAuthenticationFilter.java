package com.agro.radar.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agro.radar.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioService usuarioService;

    private List<String> excludedUrls = Arrays.asList(
        "/api/auth/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludedUrls.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = obterToken(request);

        if (token != null && jwtTokenProvider.validarToken(token)) {
            String email = jwtTokenProvider.getEmailDoToken(token);
        
            UserDetails userDetails = usuarioService.loadUserByUsername(email);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        

        filterChain.doFilter(request, response);
    }

    private String obterToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
