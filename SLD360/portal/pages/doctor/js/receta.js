validarSesion();

$(window).on('load', function() {
    cargarPacientes();
});

function cargarPacientes() {
    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('catalogos', 'damePacientes', form).then(rsl => rsl.json()).then(resultado => {
        if (!resultado.Error) {
            let pacientes = resultado.Pacientes;
            pacientes.forEach(paciente => {
                $('#cmbPacientes').append(`<option value="${paciente.id}">${paciente.apellido_uno} ${paciente.apellido_dos} ${paciente.nombre}</option>`);
            });
            
            // Establecer fecha actual
            let hoy = new Date();
            $('#txtFecha').val(hoy.toISOString().split('T')[0]);
        }
    });
}

function agregarMedicamento() {
    let nuevaFila = `
    <tr>
        <td><input type="text" class="form-control medicamento"></td>
        <td><input type="text" class="form-control presentacion"></td>
        <td><input type="text" class="form-control dosis"></td>
        <td><input type="text" class="form-control frecuencia"></td>
        <td><input type="text" class="form-control duracion"></td>
        <td><button class="btn btn-danger btn-sm" onclick="eliminarFila(this)"><i class="las la-trash"></i></button></td>
    </tr>`;
    
    $('#tblMedicamentos tbody').append(nuevaFila);
}

function eliminarFila(boton) {
    $(boton).closest('tr').remove();
}

function generarReceta() {
    let idPaciente = $('#cmbPacientes').val();
    let fecha = $('#txtFecha').val();
    let diagnostico = $('#txtDiagnostico').val();
    let indicaciones = $('#txtIndicaciones').val();
    
    if (!idPaciente || !fecha || !diagnostico) {
        alert('Por favor complete los campos obligatorios: Paciente, Fecha y Diagnóstico');
        return;
    }
    
    // Recolectar medicamentos
    let medicamentos = [];
    $('#tblMedicamentos tbody tr').each(function() {
        let medicamento = {
            nombre: $(this).find('.medicamento').val(),
            presentacion: $(this).find('.presentacion').val(),
            dosis: $(this).find('.dosis').val(),
            frecuencia: $(this).find('.frecuencia').val(),
            duracion: $(this).find('.duracion').val()
        };
        
        if (medicamento.nombre) {
            medicamentos.push(medicamento);
        }
    });
    
    if (medicamentos.length === 0) {
        alert('Debe agregar al menos un medicamento');
        return;
    }
    
    let form = new FormData();
    form.append('idPaciente', idPaciente);
    form.append('fecha', fecha);
    form.append('diagnostico', diagnostico);
    form.append('indicaciones', indicaciones);
    form.append('medicamentos', JSON.stringify(medicamentos));
    form.append('idDoctor', localStorage.getItem('idUsuario'));
    
    // Mostrar loader o mensaje de procesamiento
    const btnGenerar = $('.btn-dark-red-f-gr');
    btnGenerar.prop('disabled', true).html('<i class="las la-spinner la-spin"></i> Generando PDF...');
    
    // Guardar receta en base de datos
    peticion('receta', 'guardarReceta', form)
        .then(rsl => {
            if (!rsl.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return rsl.json();
        })
        .then(resultado => {
            if (resultado.Error) {
                throw new Error(resultado.ErrorMensaje);
            }
            
            // Crear una URL absoluta para el PDF
            const pdfUrl = new URL(`/ServicesSLD360/documents/receta.php?id=${resultado.idReceta}`, window.location.origin);
            
            // Abrir el PDF en una nueva pestaña
            const pdfWindow = window.open(pdfUrl.href, '_blank');
            
            // Si el navegador bloquea la apertura de ventanas, mostrar mensaje
            if (!pdfWindow || pdfWindow.closed || typeof pdfWindow.closed == 'undefined') {
                alert('El PDF se generó correctamente, pero tu navegador bloqueó la ventana emergente. Por favor habilita popups para este sitio o haz clic en el siguiente enlace:\n\n' + pdfUrl.href);
            }
            limpiarFormularioReceta();
        })
        .catch(error => {
            console.error('Error al generar receta:', error);
            alert('Error al generar la receta: ' + error.message);
        })
        .finally(() => {
            btnGenerar.prop('disabled', false).html('<i class="las la-file-pdf"></i> Generar Receta PDF');
        });
}

function limpiarFormularioReceta() {
    $('#txtDiagnostico').val('');
    $('#txtIndicaciones').val('');
    $('#tblMedicamentos tbody').empty();
}