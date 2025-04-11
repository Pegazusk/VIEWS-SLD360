var idPacienteEnCurso = 0;

$(window).on('load', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id') || 0;

    idPacienteEnCurso = id;

    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    // Cargar lista de pacientes
    peticion('doctores', 'damePacientes', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            resultado.Pacientes.forEach(paciente => {
                if (paciente.idPaciente == id) {
                    $('#txtNombre').val(paciente.nombrePaciente);
                    $('#txtEdad').val(paciente.edad);
                }
            })
        }
    });

    let vacunasPromise = peticion('catalogos', 'dameVacunas', form).then(rsl => rsl.json());

    Promise.all([vacunasPromise]).then(resultados => {
        let resultadoVacunas = resultados[0];

        if (!resultadoVacunas.Error) {
            let vacunas = resultadoVacunas.Vacunas;
            vacunas.forEach(vacuna => {
                $('#cmbVacunas').append(`<option value="${vacuna.id}">${vacuna.nombre}</option>`);
            });
        }
    });

    $('#cmbVacunas').on('change', function () {
        actualizarTabla();
    });

    $('#btnRegistrarDosis').on('click', function () {
        registrarDosis();
    });

    document.getElementById('btnHistorial').href = dameUrlServidor() + 'ServicesSLD360/documents/historialClinico.php?id='+idPacienteEnCurso;
    document.getElementById('btnCartilla').href = dameUrlServidor() + 'ServicesSLD360/documents/cartilla.php?id='+idPacienteEnCurso;
});

function actualizarTabla() {
    let form = new FormData();
    form.append('idUsuario', idPacienteEnCurso);
    form.append('idVacuna', $('#cmbVacunas :selected').val());

    peticion('pacientes', 'dameVacunasXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            let vacunas = resultado.Vacunas;
            $('#tblVacunas').html('');

            let index = 0;
            vacunas.forEach(vacuna => {
                index++;
                $('#tblVacunas').append('<tr><td>' + index + '</td><td>' + $('#cmbVacunas :selected').text() + '</td><td>' + (vacuna.lote == '' ? '-' : vacuna.lote) + '</td><td>' + vacuna.fecha + '</td></tr>');
            });
        }
    });
}

function registrarDosis() {
    if ($('#cmbVacunas :selected').val() == '' || $('#txtLote').val() == '') return;

    let form = new FormData();
    form.append('idUsuario', idPacienteEnCurso);
    form.append('idVacuna', $('#cmbVacunas :selected').val());
    form.append('fecha', $('#txtFechaAplicacion').val());
    form.append('lote', $('#txtLote').val());

    peticion('pacientes', 'registrarDosisVacuna', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            $("#txtFechaAplicacion").val('');
            $('#txtLote').val('');
            actualizarTabla();
        }
    });
}