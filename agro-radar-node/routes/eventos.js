const express = require('express');
const router = express.Router();
const {
  listarEventos,
  criarEvento,
  deletarEventos,
  filtrarEventos,
  deletarTodosEventos,
} = require('../controllers/eventoController');
const autenticarToken = require('../middlewares/authMiddleware');

/**
 * @swagger
 * components:
 *   schemas:
 *     Evento:
 *       type: object
 *       properties:
 *         tipo:
 *           type: string
 *           description: Tipo da mensagem.
 *         mensagem:
 *           type: string
 *           description: Conteúdo da mensagem.
 *         dispositivoId:
 *           type: number
 *           description: ID do dispositivo associado ao evento.
 *         dispositivoNome:
 *           type: string
 *           description: Nome do dispositivo.
 *         localizacao:
 *           type: string
 *           description: Localização do dispositivo.
 *         sensorId:
 *           type: number
 *           description: ID do sensor associado ao evento.
 *         timestamp:
 *           type: string
 *           format: date-time
 *           description: Momento em que o evento ocorreu.
 */

/**
 * @swagger
 * /api/eventos:
 *   get:
 *     summary: Lista todos os eventos
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Lista de eventos
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 $ref: '#/components/schemas/Evento'
 */

/**
 * @swagger
 * /api/eventos:
 *   post:
 *     summary: Cria um novo evento
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/Evento'
 *     responses:
 *       201:
 *         description: Evento criado
 */

/**
 * @swagger
 * /api/eventos:
 *   delete:
 *     summary: Deleta eventos com base nos filtros fornecidos
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: tipo
 *         schema:
 *           type: string
 *         description: Tipo do evento a ser deletado.
 *       - in: query
 *         name: timestampFim
 *         schema:
 *           type: string
 *           format: date-time
 *         description: Deleta eventos mais antigos que essa data e hora (ISO 8601).
 *     responses:
 *       200:
 *         description: Eventos deletados com sucesso
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   description: Mensagem de confirmação.
 *                 quantidade:
 *                   type: number
 *                   description: Número de eventos deletados.
 *       400:
 *         description: Nenhum filtro fornecido
 *       500:
 *         description: Erro ao deletar eventos
 */

/**
 * @swagger
 * /api/eventos/filtro:
 *   get:
 *     summary: Filtra eventos por critérios
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: tipo
 *         schema:
 *           type: string
 *         description: Tipo de mensagem
 *       - in: query
 *         name: timestampInicio
 *         schema:
 *           type: string
 *           format: date-time
 *         description: Início do intervalo de tempo (inclusive)
 *       - in: query
 *         name: timestampFim
 *         schema:
 *           type: string
 *           format: date-time
 *         description: Fim do intervalo de tempo (inclusive)
 *     responses:
 *       200:
 *         description: Eventos filtrados
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 $ref: '#/components/schemas/Evento'
 */

/**
 * @swagger
 * /api/eventos/tudo:
 *   delete:
 *     summary: Deleta todos os eventos
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Todos os eventos foram deletados com sucesso
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                 quantidade:
 *                   type: number
 *       500:
 *         description: Erro ao deletar todos os eventos
 */

// Rotas protegidas por autenticação
router.get('/eventos', autenticarToken, listarEventos);
router.post('/eventos', autenticarToken, criarEvento);
router.delete('/eventos', autenticarToken, deletarEventos);
router.get('/eventos/filtro', autenticarToken, filtrarEventos);
router.delete('/eventos/tudo', autenticarToken, deletarTodosEventos); // Nova rota

module.exports = router;
