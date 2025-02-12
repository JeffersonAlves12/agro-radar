const mongoose = require('mongoose');

/**
 * @swagger
 * components:
 *   schemas:
 *     Evento:
 *       type: object
 *       required:
 *         - tipo
 *         - mensagem
 *         - dispositivoId
 *         - sensorId
 *       properties:
 *         tipo:
 *           type: string
 *           description: Tipo do evento
 *         mensagem:
 *           type: string
 *           description: Mensagem do evento
 *         dispositivoId:
 *           type: number
 *           description: ID do dispositivo relacionado
 *         dispositivoNome:
 *           type: string
 *           description: Nome do dispositivo
 *         localizacao:
 *           type: string
 *           description: Localização do dispositivo
 *         sensorId:
 *           type: number
 *           description: ID do sensor relacionado
 *         timestamp:
 *           type: string
 *           format: date-time
 *           description: Data e hora do evento
 *       example:
 *         tipo: "alerta"
 *         mensagem: "Sensor 101 não está detectando valor"
 *         dispositivoId: 1
 *         dispositivoNome: "Máquina de Coleta"
 *         localizacao: "Campo A"
 *         sensorId: 101
 *         timestamp: "2024-11-30T01:25:00Z"
 */

const eventoSchema = new mongoose.Schema({
  tipo: { type: String, required: true },       // Tipo do evento (ex.: "login", "sensor", "dispositivo")
  mensagem: { type: String, required: true },  // Mensagem descritiva do evento
  timestamp: { type: Date, default: Date.now }, // Data e hora do evento
  detalhes: { type: Object, required: false }  // Dados adicionais do evento
});

module.exports = mongoose.model('Evento', eventoSchema);
