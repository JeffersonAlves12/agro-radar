package com.agro.radar.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro.radar.models.Dispositivo;
import com.agro.radar.models.SensorData;
import com.agro.radar.services.DispositivoService;
import com.agro.radar.services.SensorDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class MqttSubscriber {

    private static final String MQTT_BROKER = "tcp://localhost:1883";
    private static final String TOPIC = "agriculture/sensors";

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private DispositivoService dispositivoService;

    private MqttClient client;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
            client.setCallback(new SimpleMqttCallback());
            client.connect();
            client.subscribe(TOPIC);
            System.out.println("Conectado ao broker MQTT e inscrito no tópico: " + TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private class SimpleMqttCallback implements MqttCallback {

        private ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void connectionLost(Throwable cause) {
            System.out.println("Conexão perdida com o broker MQTT");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String payload = new String(message.getPayload());
            System.out.println("Mensagem recebida do tópico " + topic + ": " + payload);

            // Supondo que o payload seja um JSON com os dados do sensor
            SensorDataDTO sensorDataDTO = objectMapper.readValue(payload, SensorDataDTO.class);

            // Buscar o dispositivo pelo ID (ou criar um novo se não existir)
            Dispositivo dispositivo = dispositivoService.buscarPorId(sensorDataDTO.getDispositivoId())
                    .orElseGet(() -> {
                        Dispositivo novoDispositivo = new Dispositivo();
                        novoDispositivo.setNome("Dispositivo " + sensorDataDTO.getDispositivoId());
                        return dispositivoService.salvar(novoDispositivo);
                    });

            // Criar e salvar o SensorData
            SensorData sensorData = new SensorData();
            sensorData.setTipoSensor(sensorDataDTO.getTipoSensor());
            sensorData.setValor(sensorDataDTO.getValor());
            sensorData.setDispositivo(dispositivo);

            sensorDataService.salvar(sensorData);
            System.out.println("Dados do sensor salvos no banco de dados");
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            // Não utilizado no subscriber
        }
    }

    // DTO para mapear os dados recebidos
    private static class SensorDataDTO {
        private Long dispositivoId;
        private String tipoSensor;
        private Double valor;

        // Getters e Setters
        public Long getDispositivoId() {
            return dispositivoId;
        }

        public void setDispositivoId(Long dispositivoId) {
            this.dispositivoId = dispositivoId;
        }

        public String getTipoSensor() {
            return tipoSensor;
        }

        public void setTipoSensor(String tipoSensor) {
            this.tipoSensor = tipoSensor;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }
    }
}
