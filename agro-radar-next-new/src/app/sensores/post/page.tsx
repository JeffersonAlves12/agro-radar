'use client';

import React, { useState, useEffect, FormEvent } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

interface Dispositivo {
  id: number;
  nome: string;
  // Outros campos podem ser adicionados conforme necessário
}

export default function Page() {
  const router = useRouter();

  // Estados para os campos do sensor
  const [tipoSensor, setTipoSensor] = useState('');
  const [valor, setValor] = useState<number>(0);
  const [timestamp, setTimestamp] = useState(new Date().toISOString());
  // Em vez de uma string para "dispositivo", usaremos o id do dispositivo selecionado
  const [selectedDeviceId, setSelectedDeviceId] = useState<number | ''>('');
  const [error, setError] = useState<string | null>(null);

  // Estados para os dispositivos (para preencher o dropdown)
  const [devices, setDevices] = useState<Dispositivo[]>([]);
  const [loadingDevices, setLoadingDevices] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/dispositivos')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao buscar dispositivos');
        }
        return response.json();
      })
      .then((data: Dispositivo[]) => {
        setDevices(data);
        setLoadingDevices(false);
      })
      .catch((err) => {
        console.error('Erro ao carregar dispositivos:', err);
        setError('Erro ao carregar dispositivos.');
        setLoadingDevices(false);
      });
  }, []);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (selectedDeviceId === '') {
      setError('Selecione um dispositivo.');
      return;
    }

    // Monta o payload enviando o dispositivo como objeto (com pelo menos o id)
    const payload = {
      id: 0,
      tipoSensor,
      valor,
      timestamp,
      dispositivo: {
        id: Number(selectedDeviceId)
      }
    };

    fetch('http://localhost:8080/api/sensores', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    })
      .then((response) => {
        if (!response.ok) {
          console.error(
            `Erro ao criar sensor. Status: ${response.status} - ${response.statusText}`
          );
          response.text().then((text) => {
            console.error('Corpo da resposta:', text);
          });
          throw new Error('Erro ao criar sensor');
        }
        return response.json();
      })
      .then((data) => {
        router.push('/sensores');
      })
      .catch((err) => {
        console.error('Erro capturado:', err);
        setError('Erro ao criar sensor.');
      });
  };

  return (
    <div className="max-w-4xl mx-auto p-8 bg-green-50 rounded shadow-md">
      <h1 className="text-3xl font-bold text-center mb-8 text-green-800">
        Adicionar Sensor
      </h1>
      <form onSubmit={handleSubmit} className="space-y-8">
        {/* Campos para Tipo do Sensor e Valor */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-green-700 font-medium mb-2">
              Tipo do Sensor
            </label>
            <input
              type="text"
              value={tipoSensor}
              onChange={(e) => setTipoSensor(e.target.value)}
              className="w-full px-4 py-2 border border-green-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400 text-green-800"
              required
            />
          </div>
          <div>
            <label className="block text-green-700 font-medium mb-2">
              Valor
            </label>
            <input
              type="number"
              value={valor}
              onChange={(e) => setValor(Number(e.target.value))}
              className="w-full px-4 py-2 border border-green-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400 text-green-800"
              required
            />
          </div>
        </div>

        {/* Campo para Timestamp e seleção do Dispositivo */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-green-700 font-medium mb-2">
              Timestamp
            </label>
            <input
              type="text"
              value={timestamp}
              onChange={(e) => setTimestamp(e.target.value)}
              className="w-full px-4 py-2 border border-green-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400 text-green-800"
              required
            />
          </div>
          <div>
            <label className="block text-green-700 font-medium mb-2">
              Dispositivo
            </label>
            {loadingDevices ? (
              <p>Carregando dispositivos...</p>
            ) : (
              <select
                value={selectedDeviceId}
                onChange={(e) =>
                  setSelectedDeviceId(e.target.value === '' ? '' : Number(e.target.value))
                }
                className="w-full px-4 py-2 border border-green-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400 text-green-800"
                required
              >
                <option value="">Selecione um dispositivo</option>
                {devices.map((device) => (
                  <option key={device.id} value={device.id}>
                    {device.id} - {device.nome}
                  </option>
                ))}
              </select>
            )}
          </div>
        </div>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            {error}
          </div>
        )}

        {/* Botões de ação */}
        <div className="flex justify-between">
          <Link
            href="/sensores"
            className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-6 rounded"
          >
            Cancelar
          </Link>
          <button
            type="submit"
            className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 rounded"
          >
            Salvar
          </button>
        </div>
      </form>
    </div>
  );
}
