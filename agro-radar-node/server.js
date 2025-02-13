const express = require('express');
const mongoose = require('mongoose');
const swaggerUi = require('swagger-ui-express');
const swaggerJsdoc = require('swagger-jsdoc');
const eventosRoutes = require('./routes/eventos');
const cors = require('cors');
require('dotenv').config();

const app = express();

// Middleware para JSON
app.use(express.json());

// Libera todas as origens para testes:
app.use(cors());

// Configuração do Swagger
const swaggerOptions = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'Agro API Express',
      version: '1.0.0',
      description: 'API para gerenciar notificações de dispositivos',
    },
    servers: [
      {
        url: 'http://localhost:3001', // URL do servidor
        description: 'Servidor local',
      },
    ],
    components: {
      securitySchemes: {
        bearerAuth: {
          type: 'http',
          scheme: 'bearer',
          bearerFormat: 'JWT', // Indica que o formato do token é JWT
        },
      },
    },
    security: [
      {
        bearerAuth: [], // Aplica a autenticação em todos os endpoints por padrão
      },
    ],
  },
  apis: ['./routes/*.js'], // Caminho para as rotas que contêm as definições do Swagger
};

const swaggerDocs = swaggerJsdoc(swaggerOptions);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

// Rotas
app.use('/api', eventosRoutes);

// Conexão ao MongoDB
mongoose
  .connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => console.log('MongoDB conectado!'))
  .catch((error) => console.error('Erro ao conectar ao MongoDB:', error));

// Inicia o servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Servidor rodando na porta ${PORT}`));
