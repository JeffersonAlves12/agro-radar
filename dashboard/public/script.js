async function fetchSensorData() {
    try {
      const response = await fetch('/api/sensores');
      const data = await response.json();
      const sensorDataDiv = document.getElementById('sensorData');
  
      data.forEach(sensor => {
        const div = document.createElement('div');
        div.className = 'sensor-card';
        div.innerHTML = `
          <h3>Dispositivo ID: ${sensor.dispositivoId}</h3>
          <p>Tipo de Sensor: ${sensor.tipoSensor}</p>
          <p>Valor: ${sensor.valor}</p>
          <p>Timestamp: ${sensor.timestamp}</p>
        `;
        sensorDataDiv.appendChild(div);
      });
    } catch (error) {
      console.error('Erro ao buscar dados dos sensores:', error);
    }
  }
  
  fetchSensorData();
  