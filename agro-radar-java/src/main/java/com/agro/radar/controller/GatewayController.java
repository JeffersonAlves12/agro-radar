package com.agro.radar.controller;

import com.agro.radar.dto.GatewayDTO;
import com.agro.radar.models.Gateway;
import com.agro.radar.services.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gateways")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping
    public List<GatewayDTO> listarTodos() {
        return gatewayService.buscarTodos().stream()
            .map(GatewayDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GatewayDTO buscarPorId(@PathVariable Long id) {
        return new GatewayDTO(gatewayService.buscarPorId(id));
    }

    @PostMapping
    public GatewayDTO criar(@RequestBody Gateway gateway) {
        return new GatewayDTO(gatewayService.salvar(gateway));
    }

    @PutMapping("/{id}")
    public GatewayDTO atualizar(@PathVariable Long id, @RequestBody Gateway gateway) {
        return new GatewayDTO(gatewayService.atualizar(id, gateway));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        gatewayService.deletar(id);
    }
}