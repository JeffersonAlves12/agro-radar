'use client';

import React, { useEffect, useState } from 'react';

interface Evento {
  _id: string;
  tipo: string;
  mensagem: string;
  timestamp: string;
  detalhes?: any;
}

export default function EventoListPage() {
  const [eventos, setEventos] = useState<Evento[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Exemplo: token fixo (só para teste). 
  // Em um cenário real, você buscaria esse token do localStorage ou contexto
  // após fazer login e recebê-lo da sua API.
  const token = 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBhZ3JvLmNvbSIsImlhdCI6MzYwMCwiZXhwIjoxNzM5NDExMDE0fQ.CO47oWaScyuI8WlZ9yfhj-yKKtRwd7oSc8pahEp0CORLwll9VPSN8PR9hqYMWIeX';

  const fetchEventos = () => {
    fetch('http://localhost:3001/api/eventos', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      mode: 'cors', // Adicionado para garantir CORS
    })
      .then((response) => {
        if (!response.ok) {
          // Se der erro (ex: 401 não autorizado), lança exceção
          throw new Error('Erro na resposta da rede (talvez token inválido ou expirado)');
        }
        return response.json();
      })
      .then((data: Evento[]) => {
        setEventos(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setError('Não foi possível carregar os eventos. Tente novamente mais tarde.');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchEventos();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen bg-green-50">
        <p className="text-lg font-medium text-green-700">Carregando eventos...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center h-screen bg-green-50">
        <div className="bg-red-100 border border-red-400 text-red-700 px-6 py-4 rounded">
          {error}
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto p-6 bg-green-50 min-h-screen">
      <h1 className="text-4xl font-bold text-center text-green-800 mb-8">Lista de Eventos</h1>
      {eventos.length === 0 ? (
        <p className="text-center text-green-700">Nenhum evento cadastrado.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {eventos.map((evento) => (
            <div key={evento._id} className="bg-white border border-green-200 rounded-lg shadow p-6">
              <div className="mb-4">
                <h2 className="text-2xl font-semibold text-green-800">{evento.tipo}</h2>
                <p className="text-green-700">
                  <strong>Mensagem:</strong> {evento.mensagem}
                </p>
                <p className="text-green-700">
                  <strong>Data:</strong> {new Date(evento.timestamp).toLocaleString('pt-BR')}
                </p>

                {evento.detalhes && (
                  <div className="text-green-700 mt-2">
                    <strong>Detalhes:</strong>
                    {Object.entries(evento.detalhes).map(([key, value]) => (
                      <p key={key}>
                        <strong>{key}:</strong> {String(value)}
                      </p>
                    ))}
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
