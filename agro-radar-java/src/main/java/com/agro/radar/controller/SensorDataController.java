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
import com.agro.radar.push.PushService;
import com.agro.radar.services.MessageService;
import com.agro.radar.services.SensorDataService;

@RestController
@RequestMapping("/api/sensores")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private PushService pushService;

    @Autowired
    private MessageService messageService;

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

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Novo Sensor Criado",
            "jafelix495@gmail.com",
            String.format("O sensor %s com valor %.2f foi criado para o dispositivo %s.",
                    novoSensorData.getTipoSensor(), novoSensorData.getValor(), novoSensorData.getDispositivoNome())
        );

        // Push para criação de sensor
        pushService.enviarSensorCriadoEvento(
            novoSensorData.getId(),
            novoSensorData.getTipoSensor(),
            novoSensorData.getValor(),
            novoSensorData.getDispositivoNome()
        );

        return new SensorDataDTO(novoSensorData);
    }

    @PutMapping("/{id}")
    public SensorDataDTO atualizar(@PathVariable Long id, @RequestBody SensorData sensorDataAtualizado) {
        SensorData sensorData = sensorDataService.atualizar(id, sensorDataAtualizado);

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Sensor Atualizado",
            "jafelix495@gmail.com",
            String.format("O sensor %s com valor %.2f do dispositivo %s foi atualizado com sucesso.",
                    sensorData.getTipoSensor(), sensorData.getValor(), sensorData.getDispositivoNome())
        );

        // Push para atualização de sensor
        pushService.enviarSensorAtualizadoEvento(
            sensorData.getId(),
            sensorData.getTipoSensor(),
            sensorData.getValor(),
            sensorData.getDispositivoNome()
        );

        return new SensorDataDTO(sensorData);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        SensorData sensor = sensorDataService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        // Enviar mensagem para enviar e-mail
        messageService.sendEmailMessage(
            "Sensor Excluído",
            "jafelix495@gmail.com",
            String.format("O sensor %s com valor %.2f do dispositivo %s foi excluído com sucesso.",
                    sensor.getTipoSensor(), sensor.getValor(), sensor.getDispositivoNome())
        );

        // Push para exclusão de sensor
        pushService.enviarSensorExcluidoEvento(
            sensor.getId(),
            sensor.getTipoSensor(),
            sensor.getValor(),
            sensor.getDispositivoNome()
        );

        sensorDataService.deletarPorId(id);
    }
}