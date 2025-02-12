require('dotenv').config()
const jwt = require('jsonwebtoken');

const decodedSecret = Buffer.from(process.env.JWT_SECRET, 'base64');
//console.log('JWT_SECRET decodificado:', decodedSecret.toString('base64'));

// Middleware de autenticação
const autenticarToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1]; // Extrai o token do cabeçalho

  //console.log('Token recebido:', token);

  if (!token) {
    return res.status(401).json({ mensagem: 'Token não fornecido.' });
  }

  try {
    const decoded = jwt.verify(token, decodedSecret, { algorithms: ['HS384'] });
    //console.log('Token decodificado:', decoded);
    req.usuario = decoded;
    next();
  } catch (error) {
    res.status(403).json({ mensagem: 'Token inválido ou expirado.' });
  }
};

module.exports = autenticarToken;
