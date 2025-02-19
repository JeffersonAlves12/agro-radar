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

  // Busca todos os eventos
  const fetchEventos = () => {
    const token = localStorage.getItem('token'); 
    fetch('http://localhost:3001/api/eventos', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    })
      .then((response) => {
        if (!response.ok) {
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

  // Deleta todos os eventos
  const handleDeleteAll = () => {
    if (!window.confirm('Tem certeza que deseja deletar todos os eventos?')) {
      return;
    }

    const token = localStorage.getItem('token');
    fetch('http://localhost:3001/api/eventos/tudo', {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao deletar todos os eventos');
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        alert(`Foram deletados ${data.quantidade} eventos.`);
        // Atualiza a lista após a deleção
        fetchEventos();
      })
      .catch((err) => {
        console.error(err);
        setError('Não foi possível deletar todos os eventos. Tente novamente mais tarde.');
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

      {/* Botão para deletar todos os eventos */}
      <div className="text-center mb-8">
        <button
          onClick={handleDeleteAll}
          className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
        >
          Deletar Todos os Eventos
        </button>
      </div>

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
