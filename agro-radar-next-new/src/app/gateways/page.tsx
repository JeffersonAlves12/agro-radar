'use client';

import React, { useEffect, useState } from 'react';

interface Sensor {
  id: number;
  tipoSensor: string;
  valor: number;
  timestamp: string | null;
  dispositivoId: number;
}

interface Dispositivo {
  id: number;
  nome: string;
  localizacao: string;
  gatewayId: number;
  sensores: Sensor[];
}

interface Gateway {
  id: number;
  nome: string;
  localizacao: string;
  dispositivos: Dispositivo[];
}

export default function GateWays() {
  const [gateways, setGateways] = useState<Gateway[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Simulando o retorno da API com os dados fornecidos
    const data: Gateway[] = [
      {
        id: 1,
        nome: "Gateway Central",
        localizacao: "Fazenda Central",
        dispositivos: [
          {
            id: 2,
            nome: "Dispositivo 2",
            localizacao: "Campo B",
            gatewayId: 1,
            sensores: [
              {
                id: 3,
                tipoSensor: "Pressão",
                valor: 1013.25,
                timestamp: null,
                dispositivoId: 2,
              },
            ],
          },
          {
            id: 1,
            nome: "Dispositivo 1",
            localizacao: "Campo A",
            gatewayId: 1,
            sensores: [],
          },
        ],
      },
    ];

    // Simula um atraso na obtenção dos dados
    setTimeout(() => {
      setGateways(data);
      setLoading(false);
    }, 500);
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <p className="text-xl">Carregando gateways...</p>
      </div>
    );
  }

  if (error) {
    return <div className="text-red-600">{error}</div>;
  }

  return (
    <div className="max-w-7xl mx-auto p-6">
      <h1 className="text-4xl font-bold mb-8 text-center">Gateways</h1>
      {gateways.map((gateway) => (
        <div
          key={gateway.id}
          className="bg-gray-100 p-6 rounded-lg shadow mb-8 border border-gray-300"
        >
          <div className="mb-6">
            <h2 className="text-2xl font-bold">{gateway.nome}</h2>
            <p className="text-gray-700">{gateway.localizacao}</p>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {gateway.dispositivos.map((dispositivo) => (
              <div
                key={dispositivo.id}
                className="bg-white p-4 rounded-lg shadow border border-gray-200"
              >
                <h3 className="text-xl font-semibold mb-1">{dispositivo.nome}</h3>
                <p className="text-gray-600 mb-3">{dispositivo.localizacao}</p>
                <div>
                  <h4 className="font-bold mb-2">Sensores:</h4>
                  {dispositivo.sensores.length === 0 ? (
                    <p className="text-gray-500">Nenhum sensor cadastrado.</p>
                  ) : (
                    <ul className="space-y-2">
                      {dispositivo.sensores.map((sensor) => (
                        <li
                          key={sensor.id}
                          className="border p-2 rounded bg-gray-50"
                        >
                          <p>
                            <strong>ID:</strong> {sensor.id}
                          </p>
                          <p>
                            <strong>Tipo:</strong> {sensor.tipoSensor}
                          </p>
                          <p>
                            <strong>Valor:</strong> {sensor.valor}
                          </p>
                          <p>
                            <strong>Timestamp:</strong>{" "}
                            {sensor.timestamp
                              ? new Date(sensor.timestamp).toLocaleString()
                              : "N/A"}
                          </p>
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
}
