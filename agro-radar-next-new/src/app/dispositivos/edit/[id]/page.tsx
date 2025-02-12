'use client';

import { useParams, useRouter } from 'next/navigation';
import { useState, useEffect, FormEvent } from 'react';

interface Sensor {
  id: number;
  tipoSensor: string;
  valor: number;
  timestamp: string;
  dispositivoNome?: string;
}

interface Gateway {
  id: number;
  nome: string;
  localizacao: string;
  dispositivos: string[];
}

interface Dispositivo {
  id: number;
  nome: string;
  localizacao: string;
  // O dispositivo pode vir com dados parciais do gateway
  gateway?: Gateway;
  gatewayId?: number;
  gatewayNome?: string;
  sensores: Sensor[];
}

export default function EditDispositivo() {
  const { id } = useParams();
  const router = useRouter();

  const [dispositivo, setDispositivo] = useState<Dispositivo | null>(null);
  const [nome, setNome] = useState<string>('');
  const [localizacao, setLocalizacao] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Função para buscar os dados do dispositivo
  useEffect(() => {
    fetch(`http://localhost:8080/api/dispositivos/${id}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao buscar o dispositivo');
        }
        return response.json();
      })
      .then((data: Dispositivo) => {
        // Se o objeto não possuir o gateway completo, buscamos via endpoint de gateways
        if (!data.gateway && data.gatewayId) {
          fetch(`http://localhost:8080/api/gateways`)
            .then((response) => {
              if (!response.ok) {
                throw new Error('Erro ao buscar os gateways');
              }
              return response.json();
            })
            .then((gateways: Gateway[]) => {
              const foundGateway = gateways.find((g) => g.id === data.gatewayId);
              if (foundGateway) {
                data.gateway = foundGateway;
              } else {
                data.gateway = {
                  id: data.gatewayId!,
                  nome: data.gatewayNome || '',
                  localizacao: '',
                  dispositivos: [],
                };
              }
              setDispositivo(data);
              setNome(data.nome);
              setLocalizacao(data.localizacao);
              setLoading(false);
            })
            .catch((err) => {
              console.error(err);
              data.gateway = {
                id: data.gatewayId!,
                nome: data.gatewayNome || '',
                localizacao: '',
                dispositivos: [],
              };
              setDispositivo(data);
              setNome(data.nome);
              setLocalizacao(data.localizacao);
              setLoading(false);
            });
        } else {
          // Se já vier com o gateway completo ou não houver gatewayId
          setDispositivo(data);
          setNome(data.nome);
          setLocalizacao(data.localizacao);
          setLoading(false);
        }
      })
      .catch((err) => {
        console.error(err);
        setError('Não foi possível carregar o dispositivo.');
        setLoading(false);
      });
  }, [id]);

  // Envia a requisição PUT para atualizar os dados do dispositivo
  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!dispositivo) return;

    const updatedDevice = {
      ...dispositivo,
      nome,
      localizacao,
    };

    fetch(`http://localhost:8080/api/dispositivos/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updatedDevice),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao atualizar o dispositivo');
        }
        return response.json();
      })
      .then((data: Dispositivo) => {
        router.push('/dispositivos');
      })
      .catch((err) => {
        console.error(err);
        setError('Erro ao atualizar o dispositivo.');
      });
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-50">
        <p className="text-lg font-medium text-gray-700">Carregando...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-50">
        <div className="bg-red-100 border border-red-400 text-red-700 px-6 py-4 rounded">
          {error}
        </div>
      </div>
    );
  }

  if (!dispositivo) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-50">
        <p className="text-gray-700">Dispositivo não encontrado</p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto p-8 bg-gray-50 rounded shadow-md">
      <h1 className="text-3xl font-bold text-center mb-8 text-indigo-700">Editar Dispositivo</h1>
      <form onSubmit={handleSubmit} className="space-y-8">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Campo ID - somente leitura */}
          <div>
            <label className="block text-gray-700 font-medium mb-2">ID</label>
            <input
              type="text"
              value={dispositivo.id}
              disabled
              className="w-full px-4 py-2 border border-gray-300 rounded bg-gray-100 cursor-not-allowed text-gray-700"
            />
          </div>
          {/* Campo Nome - editável */}
          <div>
            <label className="block text-gray-700 font-medium mb-2">Nome</label>
            <input
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400 text-gray-800"
              required
            />
          </div>
          {/* Campo Localização - editável */}
          <div>
            <label className="block text-gray-700 font-medium mb-2">Localização</label>
            <input
              type="text"
              value={localizacao}
              onChange={(e) => setLocalizacao(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400 text-gray-800"
              required
            />
          </div>
          {/* Gateway ID - somente leitura */}
          <div>
            <label className="block text-gray-700 font-medium mb-2">Gateway ID</label>
            <input
              type="text"
              value={dispositivo.gateway?.id || dispositivo.gatewayId || ''}
              disabled
              className="w-full px-4 py-2 border border-gray-300 rounded bg-gray-100 cursor-not-allowed text-gray-700"
            />
          </div>
          {/* Gateway Nome - somente leitura */}
          <div>
            <label className="block text-gray-700 font-medium mb-2">Gateway Nome</label>
            <input
              type="text"
              value={dispositivo.gateway?.nome || dispositivo.gatewayNome || ''}
              disabled
              className="w-full px-4 py-2 border border-gray-300 rounded bg-gray-100 cursor-not-allowed text-gray-700"
            />
          </div>
          {/* Gateway Localização - somente leitura */}
          <div>
            <label className="block text-gray-700 font-medium mb-2">Gateway Localização</label>
            <input
              type="text"
              value={dispositivo.gateway?.localizacao || ''}
              disabled
              className="w-full px-4 py-2 border border-gray-300 rounded bg-gray-100 cursor-not-allowed text-gray-700"
            />
          </div>
        </div>

        {/* Seção de Sensores */}
        <div className="bg-white p-4 rounded shadow border border-gray-200">
          <label className="block text-lg font-medium mb-4 text-gray-800">Sensores</label>
          {dispositivo.sensores && dispositivo.sensores.length > 0 ? (
            <ul className="divide-y divide-gray-200">
              {dispositivo.sensores.map((sensor) => (
                <li key={sensor.id} className="py-3 flex flex-col sm:flex-row sm:justify-between">
                  <div className="mb-2 sm:mb-0">
                    <span className="text-gray-800 font-semibold">ID:</span> {sensor.id}
                  </div>
                  <div className="mb-2 sm:mb-0">
                    <span className="text-gray-800 font-semibold">Tipo:</span> {sensor.tipoSensor}
                  </div>
                  <div className="mb-2 sm:mb-0">
                    <span className="text-gray-800 font-semibold">Valor:</span> {sensor.valor}
                  </div>
                  <div>
                    <span className="text-gray-800 font-semibold">Data:</span>{' '}
                    {new Date(sensor.timestamp).toLocaleString('pt-BR')}
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500">Nenhum sensor cadastrado.</p>
          )}
        </div>

        {/* Botões de ação */}
        <div className="flex justify-end space-x-4">
          <button
            type="submit"
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:ring-2 focus:ring-indigo-400"
          >
            Salvar Alterações
          </button>
          <button
            type="button"
            onClick={() => router.push('/dispositivos')}
            className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:ring-2 focus:ring-gray-400"
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}
