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

export default function SensorListPage() {
  const [sensores, setSensores] = useState<Sensor[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Função para buscar os sensores
  const fetchSensores = () => {
    fetch('http://localhost:8080/api/sensores')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro na resposta da rede');
        }
        return response.json();
      })
      .then((data: Sensor[]) => {
        setSensores(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setError('Não foi possível carregar os sensores. Tente novamente mais tarde.');
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchSensores();
  }, []);

  // Função para remover um sensor
  const handleDelete = (id: number) => {
    if (!confirm('Tem certeza que deseja remover este sensor?')) return;

    fetch(`http://localhost:8080/api/sensores/${id}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao remover o sensor');
        }
        // Atualiza a lista removendo o sensor excluído
        setSensores((prev) => prev.filter((sensor) => sensor.id !== id));
      })
      .catch((err) => {
        console.error(err);
        alert('Erro ao remover o sensor');
      });
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen bg-green-50">
        <p className="text-lg font-medium text-green-700">Carregando sensores...</p>
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
      <h1 className="text-4xl font-bold text-center text-green-800 mb-8">Lista de Sensores</h1>
      {sensores.length === 0 ? (
        <p className="text-center text-green-700">Nenhum sensor cadastrado.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {sensores.map((sensor) => (
            <div key={sensor.id} className="bg-white border border-green-200 rounded-lg shadow p-6">
              <div className="mb-4">
                <h2 className="text-2xl font-semibold text-green-800">Sensor {sensor.id}</h2>
                <p className="text-green-700">
                  <strong>Tipo:</strong> {sensor.tipoSensor}
                </p>
                <p className="text-green-700">
                  <strong>Valor:</strong> {sensor.valor}
                </p>
                <p className="text-green-700">
                  <strong>Data:</strong> {new Date(sensor.timestamp).toLocaleString('pt-BR')}
                </p>
                <p className="text-green-700">
                  <strong>Dispositivo:</strong> {sensor.dispositivoNome}
                </p>
              </div>
              <div className="flex justify-between">
                <button
                  onClick={() => handleDelete(sensor.id)}
                  className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
                >
                  Remover
                </button>
                <Link
                  href={`/sensores/edit/${sensor.id}`}
                  className="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded"
                >
                  Editar
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
