validarSesion();

$(window).on('load', function() {
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

    let especialidadPromise = peticion('catalogos', 'dameEspecialidad', form).then(rsl => rsl.json());
    let pacientePromise = peticion('catalogos', 'damePacientes', form).then(rsl => rsl.json());
    let consultorioPromise = peticion('catalogos', 'dameConsultorio', form).then(rsl => rsl.json());

    Promise.all([especialidadPromise, pacientePromise, consultorioPromise]).then(resultados => {
        let resultadoEspecialidad = resultados[0];
        let resultadoPacientes = resultados[1];
        let resultadoConsultorio = resultados[2];

        if (!resultadoEspecialidad.Error) {
            let especialidades = resultadoEspecialidad.Especialidades;
            especialidades.forEach(especialidad => {
                $('#cmbEspecialidad').append(`<option value="${especialidad.id}">${especialidad.nombre}</option>`);
            });
        }

        if (!resultadoPacientes.Error) {
            let pacientes = resultadoPacientes.Pacientes;
            pacientes.forEach(paciente => {
                $('#cmbPacientes').append(`<option value="${paciente.id}">${paciente.apellido_uno + ' ' + paciente.apellido_dos + ' ' + paciente.nombre}</option>`);
            });
        }

        if (!resultadoConsultorio.Error) {
            let consultorios = resultadoConsultorio.Consultorios;
            consultorios.forEach(consultorio => {
                $('#cmbConsultorio').append(`<option value="${consultorio.id}">${consultorio.nombre}</option>`);
            });
        }

        actualizarTabla();
    }).catch(error => {
        console.error('Error al cargar los catálogos:', error);
    });
});

function registrarCita() {
    let idEspecialidad = $('#cmbEspecialidad :selected').val();
    let idPaciente = $('#cmbPacientes :selected').val();
    let idConsultorio = $('#cmbConsultorio :selected').val();
    let fecha = $("#txtFecha").val();

    if (fecha == '' || idEspecialidad == '' || idPaciente == '' || idConsultorio == '') {
        alert('Es necesario llenar los campos completos');
        return;
    }

    let form = new FormData();
    form.append('idEspecialidad', idEspecialidad);
    form.append('idPaciente', idPaciente);
    form.append('idConsultorio', idConsultorio);
    form.append('fecha', fecha);
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('doctores', 'registraCita', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
        } else {
            $("#txtFecha").val('');
            actualizarTabla();
        }
    });
}

function actualizarTabla() {
    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('doctores', 'dameCitasXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {

        } else {
            let citas = resultado.Citas;

            $('#tblCitas').html('');

            citas.forEach(function (cita) {
                $('#tblCitas').append('<tr><td>' + cita.nombrePaciente + '</td><td>' + cita.especialidad + '</td><td>' + cita.fecha + '</td><td>' + cita.consultorio + '</td></tr>');
            });
        }
    });
}