// JavaScript para los recordatorios de medicamentos
document.addEventListener('DOMContentLoaded', function() {
    // Variables
    const medNameInput = document.getElementById('med-name');
    const medDosageInput = document.getElementById('med-dosage');
    const medFrequencySelect = document.getElementById('med-frequency');
    const addMedButton = document.getElementById('add-med');
    const medList = document.getElementById('med-list');
    const emptyState = document.querySelector('.empty-state');
    
    // Array para almacenar medicamentos
    let medications = JSON.parse(localStorage.getItem('medications')) || [];
    
    // Mostrar medicamentos al cargar la página
    renderMedications();
    
    // Evento para agregar medicamento
    addMedButton.addEventListener('click', addMedication);
    
    // Función para agregar medicamento
    function addMedication() {
      const name = medNameInput.value.trim();
      const dosage = medDosageInput.value.trim();
      const frequency = medFrequencySelect.value;
      
      if (!name || !dosage) {
        alert('Por favor completa todos los campos');
        return;
      }
      
      const medication = {
        id: Date.now(),
        name,
        dosage,
        frequency,
        addedAt: new Date().toLocaleString()
      };
      
      medications.push(medication);
      saveToLocalStorage();
      renderMedications();
      resetForm();
    }
    
    // Función para renderizar medicamentos
    function renderMedications() {
      if (medications.length === 0) {
        emptyState.style.display = 'block';
        medList.innerHTML = '<li class="empty-state">No hay medicamentos agregados</li>';
        return;
      }
      
      emptyState.style.display = 'none';
      medList.innerHTML = '';
      
      medications.forEach(med => {
        const li = document.createElement('li');
        li.className = 'medication-item';
        li.innerHTML = `
          <div class="med-info">
            <h4>${med.name}</h4>
            <p>Dosis: ${med.dosage}</p>
            <p>Frecuencia: ${getFrequencyText(med.frequency)}</p>
            <small>Agregado: ${med.addedAt}</small>
          </div>
          <button class="delete-btn" data-id="${med.id}">
            <ion-icon name="trash-outline"></ion-icon>
          </button>
        `;
        medList.appendChild(li);
      });
      
      // Agregar eventos a los botones de eliminar
      document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', deleteMedication);
      });
    }
    
    // Función para eliminar medicamento
    function deleteMedication(e) {
      const id = parseInt(e.target.closest('.delete-btn').dataset.id);
      medications = medications.filter(med => med.id !== id);
      saveToLocalStorage();
      renderMedications();
    }
    
    // Función para guardar en localStorage
    function saveToLocalStorage() {
      localStorage.setItem('medications', JSON.stringify(medications));
    }
    
    // Función para resetear el formulario
    function resetForm() {
      medNameInput.value = '';
      medDosageInput.value = '';
      medFrequencySelect.value = '1';
    }
    
    // Función para obtener texto de frecuencia
    function getFrequencyText(value) {
      const options = {
        '1': 'Una vez al día',
        '2': 'Cada 12 horas',
        '3': 'Cada 8 horas',
        '4': 'Otra frecuencia'
      };
      return options[value] || value;
    }
  });

  // Inicializa EmailJS con tu Public Key
emailjs.init("H3c-rvkNfdRUjOpaB");

document.getElementById("citaForm").addEventListener("submit", function(event) {
    event.preventDefault();

    
    const nombreM = document.getElementById("nombreM").value.trim();
    const dosis = document.getElementById("dosis").value.trim();
    const frecuencia = document.getElementById("frecuencia").value.trim();

    
    const templateParams = {
      nombreM: nombreM,
      dosis: dosis,
      frecuencia: frecuencia
    };

 
    emailjs.send("service_rutk9rr", "template_msbdx4u", templateParams) 
            .then(function(response) {
            const mensajeElemento = document.getElementById("mensaje");
            mensajeElemento.textContent = "Recordatorio agendado con éxito. Se ha enviado un correo de confirmación.";
            mensajeElemento.style.color = "green";
        }, function(error) {
            const mensajeElemento = document.getElementById("mensaje");
            mensajeElemento.textContent = "Error al realizar el recordatorio. Por favor, inténtelo de nuevo.";
            mensajeElemento.style.color = "red";
        });
});