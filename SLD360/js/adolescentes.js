document.addEventListener('DOMContentLoaded', function() {
    const tempInput = document.getElementById('temperature');
    const checkTempBtn = document.getElementById('check-temp');
    const thermometerFill = document.getElementById('thermometer-fill');
    const resultContainer = document.getElementById('result-container');
    const resultTitle = document.getElementById('result-title');
    const resultMessage = document.getElementById('result-message');
    const resultAdvice = document.getElementById('result-advice');

    checkTempBtn.addEventListener('click', checkTemperature);

    function checkTemperature() {
        const temp = parseFloat(tempInput.value);
        
        if (isNaN(temp)) {
            alert('Por favor ingresa un valor numérico para la temperatura');
            return;
        }
        
        const minTemp = -10;  
        const maxTemp = 50;   
        const normalMin = 36;
        const normalMax = 37.2;
        
        let percentage = ((temp - minTemp) / (maxTemp - minTemp)) * 100;
        percentage = Math.max(0, Math.min(100, percentage)); 
        
        let fillColor;
        if (temp < normalMin) {
            fillColor = '#3498db';
        } else if (temp >= normalMin && temp <= normalMax) {
            fillColor = '#2ecc71'; 
        } else {
            fillColor = '#e74c3c'; 
        }
        
        thermometerFill.style.height = `${percentage}%`;
        thermometerFill.style.backgroundColor = fillColor;
        
        let status, advice;
        
        if (temp < 20) {
            status = '¡Temperatura Extremadamente Baja!';
            advice = 'Este valor es incompatible con la vida humana. Verifica la medición o busca ayuda médica inmediata.';
        } 
        else if (temp >= 20 && temp < 35) {
            status = 'Hipotermia Severa';
            advice = 'Busca atención médica urgente. Mientras tanto:\n• Abrígate con mantas\n• Bebe líquidos tibios\n• Busca un lugar cálido';
        }
        else if (temp >= 35 && temp < normalMin) {
            status = 'Temperatura Baja';
            advice = 'Tu temperatura está por debajo de lo normal. Recomendaciones:\n• Descansa en un lugar cálido\n• Bebe algo caliente\n• Usa ropa abrigada';
        }
        else if (temp >= normalMin && temp <= normalMax) {
            status = 'Temperatura Normal';
            advice = '✅ ¡Perfecto! Tu temperatura está en el rango saludable.\nSigue manteniendo buenos hábitos de salud.';
        }
        else if (temp > normalMax && temp < 38) {
            status = 'Febrícula';
            advice = 'Tienes una temperatura ligeramente elevada. Recomendaciones:\n• Descansa\n• Mantente hidratado\n• Monitorea tu temperatura';
        }
        else if (temp >= 38 && temp < 40) {
            status = 'Fiebre';
            advice = 'Tienes fiebre. Debes:\n• Tomar antipiréticos (si es seguro)\n• Beber líquidos\n• Reposar\n• Consultar médico si persiste';
        }
        else {
            status = '¡Fiebre Muy Alta!';
            advice = '🚨 Busca atención médica inmediata. Mientras tanto:\n• Toma antipiréticos\n• Usa paños tibios\n• No te abrigues demasiado';
        }
        
        resultTitle.textContent = status;
        resultMessage.textContent = `Temperatura ingresada: ${temp.toFixed(1)}°C`;
        resultAdvice.innerHTML = advice.replace(/\n/g, '<br>');
        
        resultContainer.className = 'result-container';
        if (temp >= normalMin && temp <= normalMax) {
            resultContainer.classList.add('normal');
        } else if (temp > normalMax) {
            resultContainer.classList.add('fever');
        } else {
            resultContainer.classList.add('low');
        }
        
        resultContainer.style.display = 'block';
    }
    tempInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            checkTemperature();
        }
    });
});