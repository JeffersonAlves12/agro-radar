package com.agro.radar.services;

import com.agro.radar.models.Gateway;
import com.agro.radar.repository.GatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    public Gateway salvar(Gateway gateway) {
        return gatewayRepository.save(gateway);
    }

    public List<Gateway> buscarTodos() {
        return gatewayRepository.findAll();
    }

    public Optional<Gateway> buscarPorId(Long id) {
        return gatewayRepository.findById(id);
    }
}

