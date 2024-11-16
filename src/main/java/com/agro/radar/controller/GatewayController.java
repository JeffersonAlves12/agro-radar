package com.agro.radar.controller;

import com.agro.radar.models.Gateway;
import com.agro.radar.services.GatewayService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gateways")
@SecurityRequirement(name = "bearerAuth")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping
    public List<Gateway> listar() {
        return gatewayService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Gateway buscarPorId(@PathVariable Long id) {
        return gatewayService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Gateway não encontrado"));
    }

    @PostMapping
    public Gateway criar(@RequestBody Gateway gateway) {
        return gatewayService.salvar(gateway);
    }

}
