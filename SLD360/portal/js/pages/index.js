localStorage.clear();

function loguear() {
    let form = new FormData();
    form.append('correo', $('#txtCorreoLogin').val());
    form.append('clave', $('#txtClaveLogin').val());
    
    peticion('auth', 'login', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            $('#lblErrorLogin').text(resultado.ErrorMensaje);
        } else {
            localStorage.setItem('idUsuario', resultado.idUsuario);

            if (resultado.idRol == 1) { 
                location.href = dameUrlServidor(true) + 'portal/pages/paciente/inicio.html';
            } else if (resultado.idRol == 2) {
                location.href = dameUrlServidor(true) + 'portal/pages/doctor/inicio.html';
            }
        }
    });
}
function registrar() {
    let form = new FormData();
    form.append('nombre', $('#txtNombreRegistro').val());
    form.append('apellidoUno', $('#txtApellidoUnoRegistro').val());
    form.append('apellidoDos', $('#txtApellidoDosRegistro').val());
    form.append('correo', $('#txtCorreoRegistro').val());
    form.append('clave', $('#txtClaveRegistro').val());

    peticion('auth', 'registro', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            $('#lblErrorRegistro').text(resultado.ErrorMensaje);
        } else {
            localStorage.setItem('idUsuario', resultado.idUsuario);

            location.href = dameUrlServidor(true) + 'portal/pages/paciente/inicio.html';
        }
    });
}