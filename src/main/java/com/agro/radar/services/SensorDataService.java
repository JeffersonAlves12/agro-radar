package com.agro.radar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro.radar.models.SensorData;
import com.agro.radar.repository.SensorDataRepository;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    /**
     * Busca todos os dados de sensores armazenados.
     * 
     * @return Lista de SensorData
     */
    public List<SensorData> buscarTodos() {
        return sensorDataRepository.findAll();
    }

    /**
     * Salva um novo dado de sensor.
     * 
     * @param sensorData Dados do sensor a serem salvos
     * @return Dados do sensor após serem salvos
     */
    public SensorData salvar(SensorData sensorData) {
        return sensorDataRepository.save(sensorData);
    }

    /**
     * Busca um dado de sensor por ID.
     * 
     * @param id ID do dado do sensor
     * @return Optional contendo o dado do sensor, se encontrado
     */
    public Optional<SensorData> buscarPorId(Long id) {
        return sensorDataRepository.findById(id);
    }

    /**
     * Remove um dado de sensor pelo ID.
     * 
     * @param id ID do dado do sensor a ser removido
     */
    public void deletarPorId(Long id) {
        sensorDataRepository.deleteById(id);
    }

    /**
     * Atualiza um dado de sensor existente.
     * 
     * @param id         ID do dado de sensor a ser atualizado
     * @param sensorData Dados atualizados do sensor
     * @return SensorData atualizado
     */
    public SensorData atualizar(Long id, SensorData sensorData) {
        Optional<SensorData> existente = sensorDataRepository.findById(id);
        if (existente.isPresent()) {
            SensorData sensorExistente = existente.get();
            sensorExistente.setTipoSensor(sensorData.getTipoSensor());
            sensorExistente.setValor(sensorData.getValor());
            sensorExistente.setDispositivo(sensorData.getDispositivo());
            return sensorDataRepository.save(sensorExistente);
        }
        throw new RuntimeException("SensorData com ID " + id + " não encontrado");
    }
}
