    // JavaScript para recursos de apoyo
    document.addEventListener('DOMContentLoaded', function() {
      // Variables
      const resourceNameInput = document.getElementById('resource-name');
      const resourceContactInput = document.getElementById('resource-contact');
      const resourceTypeSelect = document.getElementById('resource-type');
      const resourceNotesInput = document.getElementById('resource-notes');
      const addResourceButton = document.getElementById('add-resource');
      const resourceList = document.getElementById('resource-list');
      const emptyState = document.querySelector('.empty-state');
      const downloadPlanButton = document.getElementById('download-plan');
      
      // Array para almacenar recursos
      let resources = JSON.parse(localStorage.getItem('disabilityResources')) || [];
      
      // Mostrar recursos al cargar la página
      renderResources();
      
      // Evento para agregar recurso
      addResourceButton.addEventListener('click', addResource);
      
      // Evento para descargar plan de crisis
      downloadPlanButton.addEventListener('click', function() {
        alert('Descargando plantilla de plan de crisis...');
        // Aquí iría la lógica real de descarga
      });
      
      // Función para agregar recurso
      function addResource() {
        const name = resourceNameInput.value.trim();
        const contact = resourceContactInput.value.trim();
        const type = resourceTypeSelect.value;
        const notes = resourceNotesInput.value.trim();
        
        if (!name || !contact) {
          alert('Por favor completa los campos obligatorios');
          return;
        }
        
        const resource = {
          id: Date.now(),
          name,
          contact,
          type,
          notes,
          addedAt: new Date().toLocaleString()
        };
        
        resources.push(resource);
        saveToLocalStorage();
        renderResources();
        resetForm();
      }
      
      // Función para renderizar recursos
      function renderResources() {
        if (resources.length === 0) {
          emptyState.style.display = 'block';
          resourceList.innerHTML = '<li class="empty-state">No hay recursos agregados</li>';
          return;
        }
        
        emptyState.style.display = 'none';
        resourceList.innerHTML = '';
        
        resources.forEach(res => {
          const li = document.createElement('li');
          li.className = 'resource-item';
          li.innerHTML = `
            <div class="resource-info">
              <h4>${res.name}</h4>
              <p>Contacto: ${res.contact}</p>
              <p>Tipo: ${getResourceTypeText(res.type)}</p>
              ${res.notes ? `<p>Notas: ${res.notes}</p>` : ''}
              <small>Agregado: ${res.addedAt}</small>
            </div>
            <button class="delete-btn" data-id="${res.id}">
              <ion-icon name="trash-outline"></ion-icon>
            </button>
          `;
          resourceList.appendChild(li);
        });
        
        // Agregar eventos a los botones de eliminar
        document.querySelectorAll('.delete-btn').forEach(btn => {
          btn.addEventListener('click', deleteResource);
        });
      }
      
      // Función para eliminar recurso
      function deleteResource(e) {
        const id = parseInt(e.target.closest('.delete-btn').dataset.id);
        resources = resources.filter(res => res.id !== id);
        saveToLocalStorage();
        renderResources();
      }
      
      // Función para guardar en localStorage
      function saveToLocalStorage() {
        localStorage.setItem('disabilityResources', JSON.stringify(resources));
      }
      
      // Función para resetear el formulario
      function resetForm() {
        resourceNameInput.value = '';
        resourceContactInput.value = '';
        resourceTypeSelect.value = '1';
        resourceNotesInput.value = '';
      }
      
      // Función para obtener texto de tipo de recurso
      function getResourceTypeText(value) {
        const options = {
          '1': 'Profesional de salud',
          '2': 'Grupo de apoyo',
          '3': 'Servicio de emergencia',
          '4': 'Terapia/rehabilitación',
          '5': 'Otro'
        };
        return options[value] || value;
      }
    });