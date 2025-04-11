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
});