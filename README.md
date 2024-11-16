```markdown
# Agro Radar API

Agro Radar é uma API desenvolvida para aplicações IoT voltadas à agronomia. O objetivo principal é permitir a integração de dispositivos IoT que monitoram e controlam condições agrícolas, como temperatura, umidade, e localização, facilitando o gerenciamento e a tomada de decisões em propriedades rurais.

## 🔧 Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework utilizado para facilitar o desenvolvimento da API.
- **JWT (JSON Web Token)**: Implementado para autenticação e autorização seguras.
- **MQTT (Message Queuing Telemetry Transport)**: Protocolo de comunicação para dispositivos IoT.
- **PostgreSQL**: Banco de dados para persistência de dados.
- **Swagger/OpenAPI**: Ferramenta para documentação e testes interativos da API.

---

## 📁 Estrutura do Projeto

### Models

Os modelos representam as entidades principais da aplicação e refletem as tabelas do banco de dados. Algumas das entidades principais incluem:

- **Gateway**: Representa um hub central que gerencia dispositivos IoT conectados.
- **Dispositivo**: Representa os dispositivos IoT associados a um gateway.
- **SensorData**: Dados coletados pelos sensores, como temperatura e umidade.
- **Usuario**: Representa os usuários que acessam a API, contendo informações como email e senha.

---

### Controllers

Os controllers são responsáveis por expor os endpoints da API. Entre os principais, temos:

- **AuthController**: Gerencia autenticação e geração de tokens JWT.
- **GatewayController**: Permite operações CRUD nos gateways IoT.
- **DispositivoController**: Gerencia dispositivos IoT e suas interações.
- **SensorDataController**: Manipula e exibe os dados coletados pelos sensores.

---

### 🛠 Segurança

A API utiliza JWT para autenticação e autorização:

1. **Autenticação**: Os usuários obtêm um token JWT ao fazer login no endpoint `/api/auth/login`.
2. **Autorização**: Endpoints sensíveis, como `/api/gateways`, são protegidos e exigem o envio do token no cabeçalho `Authorization` no formato `Bearer <TOKEN>`.

---

### 📡 MQTT (Message Queuing Telemetry Transport)

O protocolo MQTT foi implementado para facilitar a comunicação eficiente entre os dispositivos IoT e a API, permitindo a transmissão e recepção de mensagens leves em tempo real.

---

### 📝 Documentação

A documentação interativa da API está disponível no Swagger UI, acessível em:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

1. Java 17
2. Maven 3.9+
3. PostgreSQL configurado

### Passos

1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd agro-radar
   ```

2. Configure o arquivo `application.properties` com as credenciais do banco de dados e o segredo do JWT:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/agro_radar
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   jwt.secret=SUA_CHAVE_SECRET_BASE64
   jwt.expiration-ms=86400000
   ```

3. Compile e rode o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse a API em:
   ```
   http://localhost:8080
   ```

---

## 📌 Funcionalidades Principais

- Autenticação com JWT.
- CRUD de gateways, dispositivos e dados de sensores.
- Suporte ao protocolo MQTT para comunicação em tempo real.
- Documentação interativa com Swagger.

---

## 👥 Participantes do Projeto

- **Jefferson Alves Felix**
- **Erik Silva Maia**
- **Cheng**
```