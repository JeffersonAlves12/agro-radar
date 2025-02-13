package com.agro.radar.push;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PushService {
    @Value("${t2.url}")
    private String t2BaseUrl;

    private final PushClient pushClient;
    private String token; // Token armazenado temporariamente após o login

    public PushService(PushClient pushClient) {
        this.pushClient = pushClient;
    }

    // Armazena o token gerado no login
    public void armazenarToken(String token) {
        this.token = token;
    }

    // Método para enviar evento de login
    public void enviarLoginEvento(String nome, String email) {
        if (token == null) {
            System.err.println("Erro: Token não encontrado. Faça login primeiro.");
            return;
        }

        PushEvent evento = new PushEvent(
            "login",
            "Usuário " + nome + " logou com sucesso.",
            LocalDateTime.now(),
            Map.of("usuario", nome, "email", email) // Detalhe específico: nome do usuário
        );

        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    //Push para Usuarios--------------------------------------------------
    // Push para criação de usuário
    public void enviarUsuarioCriadoEvento(String nome, String email) {
        PushEvent evento = new PushEvent(
            "usuario_criado",
            "Novo usuário " + nome + " foi criado.",
            null,
            Map.of("usuario", nome, "email", email)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    // Push para atualização de usuário
    public void enviarUsuarioAtualizadoEvento(String nome, String email) {
        PushEvent evento = new PushEvent(
            "usuario_atualizado",
            "Usuário " + nome + " foi atualizado.",
            null,
            Map.of("usuario", nome, "email", email)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    // Push para exclusão de usuário
    public void enviarUsuarioExcluidoEvento(String nome, String email) {
        PushEvent evento = new PushEvent(
            "usuario_excluido",
            "Usuário " + nome + " foi excluído.",
            null,
            Map.of("usuario", nome, "email", email)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }
    //--------------------------------------------------
    //--------------------------------------------------

    //Push para dispositivos----------------------------
    // Push para criação de dispositivo
    public void enviarDispositivoCriadoEvento(Long id, String nome, String localizacao) {
        PushEvent evento = new PushEvent(
            "dispositivo_criado",
            "Dispositivo " + nome + " foi criado.",
            null,
            Map.of("dispositivoId", id, "dispositivoNome", nome, "dispositivoLocalizacao", localizacao)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    // Push para atualização de dispositivo
    public void enviarDispositivoAtualizadoEvento(Long id, String nome, String localizacao) {
        PushEvent evento = new PushEvent(
            "dispositivo_atualizado",
            "Dispositivo " + nome + " foi atualizado.",
            null,
            Map.of("dispositivoId", id, "dispositivoNome", nome, "dispositivoLocalizacao", localizacao)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    // Push para exclusão de dispositivo
    public void enviarDispositivoExcluidoEvento(Long id, String nome, String localizacao) {
        PushEvent evento = new PushEvent(
            "dispositivo_excluido",
            "Dispositivo " + nome + " foi excluído.",
            null,
            Map.of("dispositivoId", id, "dispositivoNome", nome, "dispositivoLocalizacao", localizacao)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }
    //--------------------------------------------------
    //--------------------------------------------------

    //Push para sensores----------------------------
    // Push para criação de sensor
    public void enviarSensorCriadoEvento(Long id, String tipoSensor, Double valor, String dispositivoNome) {
        PushEvent evento = new PushEvent(
            "sensor_criado",
            "Sensor " + tipoSensor + " foi criado.",
            null,
            Map.of("sensorId", id, "tipoSensor", tipoSensor, "sensorValor", valor, "dispositivoNome", dispositivoNome)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    // Push para atualização de sensor
    public void enviarSensorAtualizadoEvento(Long id, String tipoSensor, Double valor, String dispositivoNome) {
        PushEvent evento = new PushEvent(
            "sensor_atualizado",
            "Sensor " + tipoSensor + " foi atualizado.",
            null,
            Map.of("sensorId", id, "tipoSensor", tipoSensor, "sensorValor", valor, "dispositivoNome", dispositivoNome)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }

    // Push para exclusão de sensor
    public void enviarSensorExcluidoEvento(Long id, String tipoSensor, Double valor, String dispositivoNome) {
        PushEvent evento = new PushEvent(
            "sensor_excluido",
            "Sensor " + tipoSensor + " foi excluído.",
            null,
            Map.of("sensorId", id, "tipoSensor", tipoSensor, "sensorValor", valor, "dispositivoNome", dispositivoNome)
        );
        pushClient.enviarEvento(t2BaseUrl + "/api/eventos", evento, token);
    }
    //--------------------------------------------------
    //--------------------------------------------------
}
