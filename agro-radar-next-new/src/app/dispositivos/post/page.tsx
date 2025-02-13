'use client';

import React, { useState, useEffect, FormEvent } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

interface Sensor {
  id: number;
  tipoSensor: string;
  valor: number;
  timestamp: string;
}

interface Gateway {
  id: number;
  nome: string;
  localizacao: string;
  dispositivos: string[];
}

export default function Page() {
  const router = useRouter();

  // Estados para os campos básicos
  const [nome, setNome] = useState('');
  const [localizacao, setLocalizacao] = useState('');

  // Estados para Sensor
  const [selectedSensorId, setSelectedSensorId] = useState<number | ''>('');
  const [sensors, setSensors] = useState<Sensor[]>([]);
  const [loadingSensors, setLoadingSensors] = useState(true);

  // Estados para Gateway
  const [selectedGatewayId, setSelectedGatewayId] = useState<number | ''>('');
  const [gateways, setGateways] = useState<Gateway[]>([]);
  const [loadingGateways, setLoadingGateways] = useState(true);

  const [error, setError] = useState<string | null>(null);

  // Busca os sensores disponíveis
  useEffect(() => {
    fetch('http://localhost:8080/api/sensores')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao buscar sensores');
        }
        return response.json();
      })
      .then((data: Sensor[]) => {
        setSensors(data);
        setLoadingSensors(false);
      })
      .catch((err) => {
        console.error('Erro ao carregar os sensores:', err);
        setError('Erro ao carregar os sensores.');
        setLoadingSensors(false);
      });
  }, []);

  // Busca os gateways disponíveis
  useEffect(() => {
    fetch('http://localhost:8080/api/gateways')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao buscar gateways');
        }
        return response.json();
      })
      .then((data: Gateway[]) => {
        setGateways(data);
        setLoadingGateways(false);
      })
      .catch((err) => {
        console.error('Erro ao carregar os gateways:', err);
        setError('Erro ao carregar os gateways.');
        setLoadingGateways(false);
      });
  }, []);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const selectedGateway = gateways.find(
      (g) => g.id === Number(selectedGatewayId)
    );
    if (!selectedGateway) {
      setError('Selecione um gateway válido.');
      return;
    }

    // Monta o payload conforme o formato esperado.
    // Note que removemos o campo "dispositivo" do sensor para evitar o erro.
    const payload = {
      id: 0,
      nome,
      localizacao,
      gateway: selectedGateway,
      sensores: selectedSensorId
        ? sensors
            .filter((s) => s.id === Number(selectedSensorId))
            .map((s) => ({
              id: s.id,
              tipoSensor: s.tipoSensor,
              valor: s.valor || 0,
              timestamp: new Date().toISOString(),
              // Campo "dispositivo" removido para evitar erro de deserialização
            }))
        : []
    };

    fetch('http://localhost:8080/api/dispositivos', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    })
      .then((response) => {
        if (!response.ok) {
          console.error(
            `Erro ao criar dispositivo. Status: ${response.status} - ${response.statusText}`
          );
          response.text().then((text) => {
            console.error('Corpo da resposta:', text);
          });
          throw new Error('Erro ao criar dispositivo');
        }
        return response.json();
      })
      .then((data) => {
        router.push('/dispositivos');
      })
      .catch((err) => {
        console.error('Erro capturado:', err);
        setError('Erro ao criar o dispositivo.');
      });
  };

  return (
    <div className="max-w-4xl mx-auto p-8 bg-gray-50 rounded shadow-md">
      <h1 className="text-3xl font-bold text-center mb-8 text-indigo-700">
        Adicionar Dispositivo
      </h1>
      <form onSubmit={handleSubmit} className="space-y-8">
        {/* Campos Básicos: Nome e Localização */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Nome
            </label>
            <input
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400 text-gray-800"
              required
            />
          </div>
          <div>
            <label className="block text-gray-700 font-medium mb-2">
              Localização
            </label>
            <input
              type="text"
              value={localizacao}
              onChange={(e) => setLocalizacao(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400 text-gray-800"
              required
            />
          </div>
        </div>

        {/* Seleção de Gateway */}
        <div className="mt-4">
          <label className="block text-lg font-medium mb-2 text-gray-800">
            Selecione um Gateway
          </label>
          {loadingGateways ? (
            <p>Carregando gateways...</p>
          ) : (
            <select
              value={selectedGatewayId}
              onChange={(e) => setSelectedGatewayId(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400 text-gray-800"
              required
            >
              <option value="">Selecione um gateway</option>
              {gateways.map((gateway) => (
                <option key={gateway.id} value={gateway.id}>
                  {gateway.id} - {gateway.nome}
                </option>
              ))}
            </select>
          )}
        </div>

        {/* Seleção de Sensor */}
        <div className="mt-4">
          <label className="block text-lg font-medium mb-2 text-gray-800">
            Selecione um Sensor
          </label>
          {loadingSensors ? (
            <p>Carregando sensores...</p>
          ) : (
            <select
              value={selectedSensorId}
              onChange={(e) => setSelectedSensorId(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400 text-gray-800"
            >
              <option value="">Selecione um sensor</option>
              {sensors.map((sensor) => (
                <option key={sensor.id} value={sensor.id}>
                  {sensor.id} - {sensor.tipoSensor}
                </option>
              ))}
            </select>
          )}
        </div>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            {error}
          </div>
        )}

        {/* Botões de Ação */}
        <div className="flex justify-between">
          <Link
            href="/dispositivos"
            className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:ring-2 focus:ring-gray-400"
          >
            Cancelar
          </Link>
          <button
            type="submit"
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400"
          >
            Salvar
          </button>
        </div>
      </form>
    </div>
  );
}
