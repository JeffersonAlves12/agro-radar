const Evento = require('../models/Evento');

// Lista todos os eventos
const listarEventos = async (req, res) => {
  try {
    const eventos = await Evento.find();
    res.status(200).json(eventos);
  } catch (error) {
    res.status(500).json({ error: 'Erro ao listar eventos' });
  }
};

// Método para criar e salvar o evento
const criarEvento = async (req, res) => {
  try {
      const { tipo, mensagem, timestamp, detalhes } = req.body;

      // Validação básica
      if (!tipo || !mensagem) {
          return res.status(400).json({ error: 'Os campos tipo e mensagem são obrigatórios' });
      }

      // Cria o novo evento
      const evento = new Evento({
          tipo,
          mensagem,
          timestamp, // Usa o timestamp enviado (ou o padrão definido no modelo)
          detalhes: detalhes || {}           // Usa os detalhes enviados ou um objeto vazio
      });

      // Salva no banco de dados
      const novoEvento = await evento.save();

      res.status(201).json(novoEvento); // Retorna o evento criado
  } catch (error) {
      console.error('Erro ao criar evento:', error);
      res.status(500).json({ error: 'Erro ao processar o evento' });
  }
};

const deletarEventos = async (req, res) => {
  try {
      const { tipo, timestampFim } = req.query;

      const filtros = {};
      if (tipo) filtros.tipo = tipo;

      // Adicionar filtro para mensagens mais antigas que timestampFim
      if (timestampFim) {
          filtros.timestamp = { $lte: new Date(timestampFim) }; // Menor ou igual a
      }

      // Verifica se pelo menos um filtro foi fornecido
      if (Object.keys(filtros).length === 0) {
          return res.status(400).json({ error: 'Nenhum filtro fornecido para exclusão' });
      }

      // Deletar os eventos que correspondem aos filtros
      const resultado = await Evento.deleteMany(filtros);
      res.status(200).json({
          message: 'Eventos deletados com sucesso',
          quantidade: resultado.deletedCount,
      });
  } catch (error) {
      console.error('Erro ao deletar eventos:', error);
      res.status(500).json({ error: 'Erro ao deletar eventos' });
  }
};

const deletarTodosEventos = async (req, res) => {
  try {
    // Deleta todos os documentos da coleção
    const resultado = await Evento.deleteMany({});
    res.status(200).json({
      message: 'Todos os eventos foram deletados com sucesso',
      quantidade: resultado.deletedCount,
    });
  } catch (error) {
    console.error('Erro ao deletar todos os eventos:', error);
    res.status(500).json({ error: 'Erro ao deletar todos os eventos' });
  }
};

// Filtra eventos por critérios
const filtrarEventos = async (req, res) => {
  try {
    const { tipo, timestampInicio, timestampFim } = req.query;

        const filtros = {};
        if (tipo) filtros.tipo = tipo;

        // Filtrar por intervalo de tempo (timestamp)
        if (timestampInicio || timestampFim) {
            filtros.timestamp = {};
            if (timestampInicio) filtros.timestamp.$gte = new Date(timestampInicio); // Maior ou igual a
            if (timestampFim) filtros.timestamp.$lte = new Date(timestampFim);       // Menor ou igual a
        }

    const eventosFiltrados = await Evento.find(filtros);
    res.status(200).json(eventosFiltrados);
  } catch (error) {
    res.status(500).json({ error: 'Erro ao filtrar eventos' });
  }
};

module.exports = {
  listarEventos,
  criarEvento,
  deletarEventos,
  filtrarEventos,
  deletarTodosEventos,
};
