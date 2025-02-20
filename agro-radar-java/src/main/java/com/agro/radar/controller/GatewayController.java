package com.agro.radar.controller;

import com.agro.radar.dto.GatewayDTO;
import com.agro.radar.models.Gateway;
import com.agro.radar.services.GatewayService;
import com.agro.radar.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gateways")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private MessageService messageService;

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
        Gateway novoGateway = gatewayService.salvar(gateway);

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Novo Gateway Criado",
            "jafelix495@gmail.com",
            String.format("O gateway %s foi criado com sucesso na localização %s.",
                    novoGateway.getNome(), novoGateway.getLocalizacao())
        );

        return new GatewayDTO(novoGateway);
    }

    @PutMapping("/{id}")
    public GatewayDTO atualizar(@PathVariable Long id, @RequestBody Gateway gateway) {
        Gateway gatewayAtualizado = gatewayService.atualizar(id, gateway);

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Gateway Atualizado",
            "jafelix495@gmail.com",
            String.format("O gateway %s na localização %s foi atualizado com sucesso.",
                    gatewayAtualizado.getNome(), gatewayAtualizado.getLocalizacao())
        );

        return new GatewayDTO(gatewayAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Gateway gateway = gatewayService.buscarPorId(id);

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Gateway Excluído",
            "jafelix495@gmail.com",
            String.format("O gateway %s na localização %s foi excluído com sucesso.",
                    gateway.getNome(), gateway.getLocalizacao())
        );

        gatewayService.deletar(id);
    }
}