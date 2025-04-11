$(window).on('load', function () {
    // Cargar datos del doctor
    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('doctores', 'dameGeneralesXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            $('.txtNombrePersona').text(resultado.nombreDoctor);
            $('.txtTotalPacientes').text(resultado.totalPacientes == null ? '0' : resultado.totalPacientes);
            $('.txtCitasTotales').text(resultado.totalCitasHoy);
        }
    });

    // Cargar lista de pacientes
    peticion('doctores', 'damePacientes', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            resultado.Pacientes.forEach(paciente => {
                // Tarjeta para el paciente (sección de pacientes)
                let patientCardHTML = `
                    <div class="col-md-3">
                        <a href="mispacientes-detalle.html?id=${paciente.idPaciente}" style="color: black">
                            <div class="card">
                                <div class="card-header">
                                    <div class="card-img-top">
                                        <img class="rounded-circle" src="../../../img/people.svg" loading="lazy" />
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="card-subsection-title">
                                        <h5>${paciente.nombrePaciente}</h5>
                                        <p class="text-muted">ID: ${paciente.idPaciente}</p>
                                    </div>
                                    <div class="card-subsection-body">
                                        <label class="text-muted">Edad</label>
                                        <p>${paciente.edad}</p>
                                        <label class="text-muted">Fecha de nacimiento</label>
                                        <p>${paciente.nacimiento}</p>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <button class="btn btn-sm btn-primary btn-view-recipes" data-patient-id="${paciente.idPaciente}">
                                        Ver recetas
                                    </button>
                                </div>
                            </div>
                        </a>
                    </div>
                `;
                $('#patients-list').append(patientCardHTML);
            });

            // Configurar evento para botones de ver recetas
            $(document).on('click', '.btn-view-recipes', function (e) {
                e.preventDefault();
                e.stopPropagation();
                const patientId = $(this).data('patient-id');
                cargarRecetasPorPaciente(patientId);
            });
        }
    });
});

// Función para cargar recetas por paciente
function cargarRecetasPorPaciente(patientId) {
    $('#recetas-list').html('<div class="col-12 text-center"><i class="las la-spinner la-spin"></i> Cargando recetas...</div>');

    let form = new FormData();
    form.append('idPaciente', patientId);
    form.append('idDoctor', localStorage.getItem('idUsuario'));

    console.log('Enviando solicitud para recetas con:', {
        idPaciente: patientId,
        idDoctor: localStorage.getItem('idUsuario')
    });

    peticion('receta', 'obtenerRecetasPorPaciente', form)
        .then(async response => {
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Error HTTP: ${response.status} - ${errorText}`);
            }
            return response.json();
        })
        .then(resultado => {
            console.log('Respuesta del servidor:', resultado);

            $('#recetas-list').empty();

            if (resultado.Error) {
                throw new Error(resultado.ErrorMensaje || "Error desconocido al cargar recetas");
            }

            if (!resultado.Recetas || resultado.Recetas.length === 0) {
                $('#recetas-list').html(`
                    <div class="col-12 text-center text-muted py-4">
                        <i class="las la-file-medical-alt la-3x mb-3"></i>
                        <p>No se encontraron recetas para este paciente</p>
                    </div>
                `);
                return;
            }

            // Mostrar recetas
            resultado.Recetas.forEach(receta => {
                const fechaFormateada = new Date(receta.fecha).toLocaleDateString('es-MX', {
                    day: '2-digit',
                    month: 'long',
                    year: 'numeric'
                });

                let recetaCardHTML = `
                    <div class="col-md-4 mb-4">
                        <div class="card h-100">
                            <div class="card-header bg-light">
                                <h5 class="mb-1">Receta #${receta.id}</h5>
                                <small class="text-muted">${fechaFormateada}</small>
                                <span class="badge badge-primary float-right">${receta.medicamentos.length} medicamentos</span>
                            </div>
                            <div class="card-body">
                                <p><strong>Diagnóstico:</strong> ${receta.diagnostico || 'No especificado'}</p>
                                ${receta.indicaciones ? `<p><strong>Indicaciones:</strong> ${receta.indicaciones.substring(0, 100)}${receta.indicaciones.length > 100 ? '...' : ''}</p>` : ''}
                            </div>
                            <div class="card-footer bg-white text-right">
    <a href="${dameUrlServidor()}ServicesSLD360/documents/receta.php?id=${receta.id}" 
       class="btn btn-sm btn-success" target="_blank">
        <i class="las la-file-medical"></i> Ver Receta
    </a>
</div>
                        </div>
                    </div>
                `;
                $('#recetas-list').append(recetaCardHTML);
            });
        })
        .catch(error => {
            console.error('Error al cargar recetas:', error);
            $('#recetas-list').html(`
                <div class="col-12 text-center text-danger py-4">
                    <i class="las la-exclamation-triangle la-3x mb-3"></i>
                    <p>${error.message}</p>
                    <button class="btn btn-sm btn-outline-primary mt-2" onclick="cargarRecetasPorPaciente(${patientId})">
                        <i class="las la-sync"></i> Reintentar
                    </button>
                </div>
            `);
        });
}