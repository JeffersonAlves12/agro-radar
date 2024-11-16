package com.agro.radar.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.agro.radar.models.Dispositivo;
import com.agro.radar.models.Gateway;
import com.agro.radar.models.SensorData;
import com.agro.radar.models.Usuario;
import com.agro.radar.repository.DispositivoRepository;
import com.agro.radar.repository.GatewayRepository;
import com.agro.radar.services.UsuarioService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // Criar um gateway de exemplo
        Gateway gateway = new Gateway();
        gateway.setNome("Gateway Central");
        gateway.setLocalizacao("Fazenda Central");

        // Persistir o gateway
        gateway = gatewayRepository.save(gateway);

        // Criar dispositivos de exemplo
        Dispositivo dispositivo1 = new Dispositivo();
        dispositivo1.setNome("Dispositivo 1");
        dispositivo1.setLocalizacao("Campo A");
        dispositivo1.setGateway(gateway);

        Dispositivo dispositivo2 = new Dispositivo();
        dispositivo2.setNome("Dispositivo 2");
        dispositivo2.setLocalizacao("Campo B");
        dispositivo2.setGateway(gateway);

        // Criar sensores para o dispositivo 1
        SensorData sensor1 = new SensorData();
        sensor1.setTipoSensor("Temperatura");
        sensor1.setValor(25.3);
        sensor1.setDispositivo(dispositivo1);

        SensorData sensor2 = new SensorData();
        sensor2.setTipoSensor("Umidade");
        sensor2.setValor(65.2);
        sensor2.setDispositivo(dispositivo1);

        // Associar os sensores ao dispositivo 1
        dispositivo1.setSensores(Arrays.asList(sensor1, sensor2));

        // Persistir os dispositivos e sensores
        dispositivoRepository.saveAll(Arrays.asList(dispositivo1, dispositivo2));

        // Criar usuários de exemplo
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Administrador");
        usuario1.setEmail("admin@agro.com");
        usuario1.setSenha("admin123"); // Senha será codificada pelo serviço

        Usuario usuario2 = new Usuario();
        usuario2.setNome("João");
        usuario2.setEmail("joão@agro.com");
        usuario2.setSenha("joao123"); // Senha será codificada pelo serviço

        Usuario usuario3 = new Usuario();
        usuario3.setNome("Maria");
        usuario3.setEmail("maria@agro.com");
        usuario3.setSenha("maria123");

        // Salvar usuários utilizando o serviço
        usuarioService.salvar(usuario1);
        usuarioService.salvar(usuario2);
        usuarioService.salvar(usuario3);

        System.out.println("Dados de teste inicializados no banco de dados!");
    }
}
