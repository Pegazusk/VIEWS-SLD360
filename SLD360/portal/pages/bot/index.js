document.addEventListener('DOMContentLoaded', function() {
    const contenedorMensajes = document.getElementById('mensajes-chat');
    const entradaUsuario = document.getElementById('entrada-usuario');
    const botonEnviar = document.getElementById('boton-enviar');

    function agregarMensaje(contenido, esUsuario = false) {
        const divMensaje = document.createElement('div');
        divMensaje.className = `mensaje ${esUsuario ? 'mensaje-usuario' : 'mensaje-bot'}`;
        
        const ahora = new Date();
        const horaFormateada = ahora.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        
        divMensaje.innerHTML = `
            ${contenido}
            <div class="marca-tiempo">${horaFormateada}</div>
        `;
        
        contenedorMensajes.appendChild(divMensaje);
        contenedorMensajes.scrollTop = contenedorMensajes.scrollHeight;
    }

    function mostrarEscribiendo() {
        const divEscribiendo = document.createElement('div');
        divEscribiendo.className = 'indicador-escribiendo';
        divEscribiendo.id = 'indicador-escribiendo';
        divEscribiendo.innerHTML = `
            <span></span>
            <span></span>
            <span></span>
        `;
        contenedorMensajes.appendChild(divEscribiendo);
        contenedorMensajes.scrollTop = contenedorMensajes.scrollHeight;
    }

    function ocultarEscribiendo() {
        const indicador = document.getElementById('indicador-escribiendo');
        if (indicador) {
            indicador.remove();
        }
    }

    function enviarMensaje() {
        const mensaje = entradaUsuario.value.trim();
        if (mensaje) {
            agregarMensaje(mensaje, true);
            entradaUsuario.value = '';
            
            mostrarEscribiendo();

            let formulario = new FormData();
            formulario.append('mensaje', mensaje);
            
            peticion('bot', 'consultar', formulario)
                .then(respuesta => respuesta.json())
                .then(resultado => {
                    if (resultado.Error) {
                        ocultarEscribiendo();
                        agregarMensaje("Lo siento, hubo un error al procesar tu mensaje.");
                        console.error('Error:', resultado.ErrorMensaje);
                    } else {
                        ocultarEscribiendo();
                        agregarMensaje(resultado.Mensaje);
                    }
                });
        }
    }

    botonEnviar.addEventListener('click', enviarMensaje);
    entradaUsuario.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            enviarMensaje();
        }
    });

    setTimeout(() => {
        mostrarEscribiendo();
        setTimeout(() => {
            ocultarEscribiendo();
            agregarMensaje("Puedes preguntarme sobre síntomas, medicamentos o consejos de salud. ¿Qué te gustaría saber?");
        }, 1500);
    }, 2000);
});