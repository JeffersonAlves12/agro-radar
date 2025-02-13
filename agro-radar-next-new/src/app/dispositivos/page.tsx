'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';

interface Sensor {
  id: number;
  tipoSensor: string;
  valor: number;
  timestamp: string;
  dispositivoNome: string;
}

interface Dispositivo {
  id: number;
  nome: string;
  localizacao: string;
  gatewayId: number;
  gatewayNome: string;
  sensores: Sensor[];
}

export default function Page() {
  const [dispositivos, setDispositivos] = useState<Dispositivo[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Função para buscar os dispositivos da API
  const fetchDispositivos = () => {
    fetch('http://localhost:8080/api/dispositivos')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro na resposta da rede');
        }
        return response.json();
      })
      .then((data: Dispositivo[]) => {
        setDispositivos(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Erro ao carregar os dispositivos:', err);
        setError('Não foi possível carregar os dispositivos. Tente novamente mais tarde.');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchDispositivos();
  }, []);

  // Função para remover um dispositivo
  const handleDelete = (id: number) => {
    if (!confirm('Tem certeza que deseja remover este dispositivo?')) return;

    fetch(`http://localhost:8080/api/dispositivos/${id}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao remover o dispositivo');
        }
        // Atualiza a lista removendo o dispositivo excluído
        setDispositivos((prev) => prev.filter((dispositivo) => dispositivo.id !== id));
      })
      .catch((err) => {
        console.error(err);
        alert('Erro ao remover o dispositivo');
      });
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-lg font-medium">Carregando...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center h-screen">
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
          {error}
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto p-4">
      <h1 className="text-3xl font-bold text-center mb-6">Lista de Dispositivos</h1>
      {/* Botão centralizado para adicionar dispositivo */}
      <div className="flex justify-center mb-6">
        <Link
          href="/dispositivos/post"
          className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded"
        >
          Adicionar Dispositivo
        </Link>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {dispositivos.map((dispositivo) => (
          <div key={dispositivo.id} className="bg-white rounded-lg shadow p-6 flex flex-col justify-between">
            <div>
              <h2 className="text-xl font-semibold mb-2">{dispositivo.nome}</h2>
              <p className="text-gray-700 mb-1">
                <strong>Localização:</strong> {dispositivo.localizacao}
              </p>
              <p className="text-gray-700 mb-3">
                <strong>Gateway:</strong> {dispositivo.gatewayNome}
              </p>
              <hr className="my-3" />
              <h3 className="text-lg font-medium mb-2">Sensores</h3>
              {dispositivo.sensores && dispositivo.sensores.length > 0 ? (
                <ul className="divide-y divide-gray-200">
                  {dispositivo.sensores.map((sensor) => (
                    <li key={sensor.id} className="py-2">
                      <p className="text-gray-800">
                        <strong>Tipo:</strong> {sensor.tipoSensor}
                      </p>
                      <p className="text-gray-800">
                        <strong>Valor:</strong> {sensor.valor}
                      </p>
                      <p className="text-gray-600 text-sm">
                        <strong>Data:</strong> {new Date(sensor.timestamp).toLocaleString('pt-BR')}
                      </p>
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="text-gray-500">Nenhum sensor cadastrado.</p>
              )}
            </div>
            {/* Botões de ação */}
            <div className="mt-4 flex justify-between">
              <button
                onClick={() => handleDelete(dispositivo.id)}
                className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
              >
                Remover
              </button>
              <Link
                href={`/dispositivos/edit/${dispositivo.id}`}
                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              >
                Editar
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
