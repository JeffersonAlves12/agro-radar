const express = require('express');
const axios = require('axios');

const app = express();
const PORT = 3000;

// Middleware para servir arquivos estáticos da pasta public
app.use(express.static('public'));

// Endpoint para obter os dados dos sensores (proxy para a API do backend)
app.get('/api/sensores', async (req, res) => {
  try {
    const response = await axios.get('http://localhost:8080/api/sensores');
    res.json(response.data);
  } catch (error) {
    console.error('Erro ao buscar dados dos sensores:', error.message);
    res.status(500).send('Erro ao obter dados dos sensores');
  }
});

app.get('/api/dispositivos', async (req, res) => {
    try {
      const response = await axios.get('http://localhost:8080/api/dispositivos');
      res.json(response.data);
    } catch (error) {
      console.error('Erro ao buscar dispositivos:', error.message);
      res.status(500).send('Erro ao obter dispositivos');
    }
  });
  
  

// Inicializa o servidor
app.listen(PORT, () => {
  console.log(`Servidor rodando em: http://localhost:${PORT}`);
});
