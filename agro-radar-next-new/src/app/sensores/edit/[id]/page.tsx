'use client';

import { useParams, useRouter } from 'next/navigation';
import { useEffect, useState, FormEvent } from 'react';

interface Sensor {
  id: number;
  tipoSensor: string;
  valor: number;
  timestamp: string;
  dispositivoNome: string;
}

export default function EditSensor() {
  const { id } = useParams();
  const router = useRouter();

  const [sensor, setSensor] = useState<Sensor | null>(null);
  const [tipoSensor, setTipoSensor] = useState<string>('');
  const [valor, setValor] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Busca os dados do sensor pelo id ao montar o componente
  useEffect(() => {
    fetch(`http://localhost:8080/api/sensores/${id}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao buscar o sensor');
        }
        return response.json();
      })
      .then((data: Sensor) => {
        setSensor(data);
        setTipoSensor(data.tipoSensor);
        setValor(data.valor);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setError('Não foi possível carregar o sensor.');
        setLoading(false);
      });
  }, [id]);

  // Ao submeter, atualizamos o sensor com os novos valores e o timestamp com o horário atual
  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!sensor) return;

    const updatedSensor: Sensor = {
      ...sensor,
      tipoSensor: tipoSensor,
      valor: valor,
      timestamp: new Date().toISOString(), // Atualiza o timestamp para o horário atual
    };

    fetch(`http://localhost:8080/api/sensores/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updatedSensor),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Erro ao atualizar o sensor');
        }
        return response.json();
      })
      .then(() => {
        router.push('/sensores');
      })
      .catch((err) => {
        console.error(err);
        setError('Erro ao atualizar o sensor.');
      });
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen bg-green-50">
        <p className="text-lg font-medium text-green-700">Carregando sensor...</p>
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

  if (!sensor) {
    return (
      <div className="flex items-center justify-center h-screen bg-green-50">
        <p className="text-green-700">Sensor não encontrado</p>
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto p-8 bg-green-50 rounded shadow-md">
      <h1 className="text-3xl font-bold text-center mb-8 text-green-800">
        Editar Sensor
      </h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Campo ID - somente leitura */}
          <div>
            <label className="block text-green-700 font-medium mb-2">ID</label>
            <input
              type="text"
              value={sensor.id}
              disabled
              className="w-full px-4 py-2 border border-green-300 rounded bg-green-100 cursor-not-allowed text-green-700"
            />
          </div>
          {/* Campo Tipo de Sensor - editável */}
          <div>
            <label className="block text-green-700 font-medium mb-2">
              Tipo de Sensor
            </label>
            <input
              type="text"
              value={tipoSensor}
              onChange={(e) => setTipoSensor(e.target.value)}
              className="w-full px-4 py-2 border border-green-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400 text-green-800"
              required
            />
          </div>
          {/* Campo Valor - editável */}
          <div>
            <label className="block text-green-700 font-medium mb-2">Valor</label>
            <input
              type="number"
              value={valor}
              onChange={(e) => setValor(parseFloat(e.target.value))}
              className="w-full px-4 py-2 border border-green-300 rounded focus:outline-none focus:ring-2 focus:ring-green-400 text-green-800"
              step="0.1"
              required
            />
          </div>
          {/* Campo Horário de Modificação - somente leitura (mostrando o horário atual) */}
          <div>
            <label className="block text-green-700 font-medium mb-2">
              Horário de Modificação
            </label>
            <input
              type="text"
              value={new Date().toLocaleString('pt-BR')}
              disabled
              className="w-full px-4 py-2 border border-green-300 rounded bg-green-100 cursor-not-allowed text-green-700"
            />
          </div>
          {/* Campo Dispositivo - somente leitura */}
          <div className="md:col-span-2">
            <label className="block text-green-700 font-medium mb-2">
              Dispositivo
            </label>
            <input
              type="text"
              value={sensor.dispositivoNome}
              disabled
              className="w-full px-4 py-2 border border-green-300 rounded bg-green-100 cursor-not-allowed text-green-700"
            />
          </div>
        </div>
        <div className="flex justify-end space-x-4">
          <button
            type="submit"
            className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:ring-2 focus:ring-green-400"
          >
            Salvar Alterações
          </button>
          <button
            type="button"
            onClick={() => router.push('/sensores')}
            className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-6 rounded focus:outline-none focus:ring-2 focus:ring-gray-400"
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}
