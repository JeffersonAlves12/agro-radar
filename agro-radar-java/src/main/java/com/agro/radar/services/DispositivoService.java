package com.agro.radar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro.radar.models.Dispositivo;
import com.agro.radar.models.Gateway;
import com.agro.radar.repository.DispositivoRepository;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    public Dispositivo salvar(Dispositivo dispositivo) {
        if (dispositivo.getGateway() != null) {
            Gateway gateway = dispositivo.getGateway();
            gateway.getDispositivos().add(dispositivo);
        }
        return dispositivoRepository.save(dispositivo);
    }

    public List<Dispositivo> buscarTodos() {
        return dispositivoRepository.findAll();
    }

    public Optional<Dispositivo> buscarPorId(Long id) {
        return dispositivoRepository.findById(id);
    }

    public Dispositivo atualizar(Long id, Dispositivo dispositivoAtualizado) {
        return dispositivoRepository.findById(id)
                .map(dispositivo -> {
                    dispositivo.setNome(dispositivoAtualizado.getNome());
                    dispositivo.setLocalizacao(dispositivoAtualizado.getLocalizacao());
                    dispositivo.setGateway(dispositivoAtualizado.getGateway());
                    // Atualize outros campos se necessário
                    return dispositivoRepository.save(dispositivo);
                })
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
    }

    public void deletar(Long id) {
        dispositivoRepository.deleteById(id);
    }
}
