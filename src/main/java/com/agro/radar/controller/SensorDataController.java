package com.agro.radar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro.radar.dto.SensorDataDTO;
import com.agro.radar.models.SensorData;
import com.agro.radar.services.SensorDataService;

@RestController
@RequestMapping("/api/sensores")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @GetMapping
    public List<SensorDataDTO> listar() {
        return sensorDataService.buscarTodos()
                .stream()
                .map(SensorDataDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDataDTO buscarPorId(@PathVariable Long id) {
        SensorData sensorData = sensorDataService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("SensorData não encontrado"));
        return new SensorDataDTO(sensorData);
    }

    @PostMapping
    public SensorDataDTO criar(@RequestBody SensorData sensorData) {
        SensorData novoSensorData = sensorDataService.salvar(sensorData);
        return new SensorDataDTO(novoSensorData);
    }

    @PutMapping("/{id}")
    public SensorDataDTO atualizar(@PathVariable Long id, @RequestBody SensorData sensorDataAtualizado) {
        SensorData sensorData = sensorDataService.atualizar(id, sensorDataAtualizado);
        return new SensorDataDTO(sensorData);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        sensorDataService.deletarPorId(id);
    }
}
