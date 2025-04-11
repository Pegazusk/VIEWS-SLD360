validarSesion();

$(window).on('load', function() {
    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('pacientes', 'dameGeneralesXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            $('.txtNombrePersona').text(resultado.nombrePersona);
        }
    });

    peticion('pacientes', 'dameCitasXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {

        } else {
            let citas = resultado.Citas;

            $('#tblCitas').html('');

            citas.forEach(function (cita) {
                $('#tblCitas').append('<tr><td>' + cita.nombreDoctor + '</td><td>' + cita.especialidad + '</td><td>' + cita.fecha + '</td><td>' + cita.consultorio + '</td></tr>');
            });
        }
    });
});