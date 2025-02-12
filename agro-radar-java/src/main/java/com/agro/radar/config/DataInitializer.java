package com.agro.radar.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.agro.radar.models.Dispositivo;
import com.agro.radar.models.Gateway;
import com.agro.radar.models.SensorData;
import com.agro.radar.models.Usuario;
import com.agro.radar.repository.DispositivoRepository;
import com.agro.radar.repository.GatewayRepository;
import com.agro.radar.repository.SensorDataRepository;
import com.agro.radar.services.UsuarioService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // Criar e salvar o gateway
        Gateway gateway = new Gateway();
        gateway.setNome("Gateway Central");
        gateway.setLocalizacao("Fazenda Central");
        gateway = gatewayRepository.save(gateway);

        // Criar dispositivos associados ao gateway
        Dispositivo dispositivo1 = criarDispositivo("Dispositivo 1", "Campo A", gateway);
        Dispositivo dispositivo2 = criarDispositivo("Dispositivo 2", "Campo B", gateway);

        // Criar e associar sensores aos dispositivos
        criarSensor("Temperatura", 25.3, dispositivo1);
        criarSensor("Umidade", 65.2, dispositivo1);
        criarSensor("Pressão", 1013.25, dispositivo2); // Sensor adicional para dispositivo2

        // Criar usuários
        criarUsuario("Administrador", "admin@agro.com", "admin123");
        criarUsuario("João", "joão@agro.com", "joao123");
        criarUsuario("Maria", "maria@agro.com", "maria123");

        System.out.println("Dados inicializados com sucesso!");
    }

    private Dispositivo criarDispositivo(String nome, String localizacao, Gateway gateway) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNome(nome);
        dispositivo.setLocalizacao(localizacao);
        dispositivo.setGateway(gateway);
        return dispositivoRepository.save(dispositivo);
    }

    private void criarSensor(String tipo, double valor, Dispositivo dispositivo) {
        SensorData sensor = new SensorData();
        sensor.setTipoSensor(tipo);
        sensor.setValor(valor);
        sensor.setDispositivo(dispositivo);
        sensorDataRepository.save(sensor);
        
        // Atualizar a lista de sensores do dispositivo
        dispositivo.getSensores().add(sensor);
        dispositivoRepository.save(dispositivo);
    }

    private void criarUsuario(String nome, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuarioService.salvar(usuario);
    }
}