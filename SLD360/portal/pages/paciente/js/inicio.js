$(window).on('load', function() {
    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('pacientes', 'dameGeneralesXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            $('.txtNombrePersona').text(resultado.nombrePersona);
            $('.txtCitasProximas').text(resultado.citasProximas);
            $('.txtCitasTotales').text(resultado.citasTotales);
        }
    });
});