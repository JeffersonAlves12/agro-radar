package com.agro.radar.services;

import com.agro.radar.models.Gateway;
import com.agro.radar.repository.GatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    public List<Gateway> buscarTodos() {
        return gatewayRepository.findAllWithDispositivosAndSensores();
    }

    public Gateway salvar(Gateway gateway) {
        return gatewayRepository.save(gateway);
    }

    public Gateway buscarPorId(Long id) {
        return gatewayRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Gateway não encontrado"));
    }

    public Gateway atualizar(Long id, Gateway gatewayAtualizado) {
        return gatewayRepository.findById(id)
            .map(gateway -> {
                gateway.setNome(gatewayAtualizado.getNome());
                gateway.setLocalizacao(gatewayAtualizado.getLocalizacao());
                return gatewayRepository.save(gateway);
            })
            .orElseThrow(() -> new RuntimeException("Gateway não encontrado"));
    }

    public void deletar(Long id) {
        gatewayRepository.deleteById(id);
    }
}