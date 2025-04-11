// comunidad-rural.js
document.addEventListener('DOMContentLoaded', function () {
    // Función para abrir WhatsApp (dos botones)
    function setupWhatsAppButton(buttonId) {
        document.getElementById(buttonId).addEventListener("click", function () {
            const telefono = '521234567890';
            const mensaje = 'Hola, necesito asistencia médica para comunidad rural.';
            const url = `https://wa.me/${telefono}?text=${encodeURIComponent(mensaje)}`;
            window.open(url, '_blank');
        });
    }
    
    setupWhatsAppButton("whatsappButton");
    setupWhatsAppButton("whatsappButton2");

    // Control de la cámara para videollamadas (dos botones)
    function setupCameraToggle(buttonId, videoId) {
        const video = document.getElementById(videoId);
        const toggleButton = document.getElementById(buttonId);
        let stream = null;

        toggleButton.addEventListener("click", async function () {
            if (!stream) {
                try {
                    stream = await navigator.mediaDevices.getUserMedia({ video: true });
                    video.srcObject = stream;
                    video.classList.remove("d-none");
                    toggleButton.textContent = "Apagar Cámara";
                } catch (error) {
                    console.error("Error al acceder a la cámara:", error);
                    alert("No se pudo acceder a la cámara. Por favor verifica los permisos.");
                }
            } else {
                let tracks = stream.getTracks();
                tracks.forEach(track => track.stop());
                video.srcObject = null;
                video.classList.add("d-none");
                stream = null;
                toggleButton.textContent = buttonId.includes("2") ? "Iniciar Videollamada" : "Encender Cámara";
            }
        });
    }
    
    setupCameraToggle("toggleCamera", "videoStream");
    setupCameraToggle("toggleCamera2", "videoStream2");

    // Función para descargar guías
    function descargarGuia() {
        alert("Descargando todas las guías en formato ZIP...");
        // Aquí iría la lógica real para descargar el archivo ZIP
    }
    
    // Asignar la función al botón correspondiente
    const downloadButtons = document.querySelectorAll("[onclick='descargarGuia()']");
    downloadButtons.forEach(button => {
        button.addEventListener("click", descargarGuia);
    });

    // Manejo de pestañas
    const tabButtons = document.querySelectorAll(".tab-btn");
    tabButtons.forEach(button => {
        button.addEventListener("click", function() {
            // Remover clase active de todos los botones
            tabButtons.forEach(btn => btn.classList.remove("active"));
            // Agregar clase active al botón clickeado
            this.classList.add("active");
            
            // Cambiar el contenido según la pestaña
            const tabText = document.querySelector(".tab-text");
            if (this.textContent === "Cobertura") {
                tabText.textContent = "Contamos con 15 unidades móviles que recorren más de 200 comunidades rurales cada mes, brindando atención médica básica, vacunación y controles preventivos.";
            } else if (this.textContent === "Servicios") {
                tabText.textContent = "Nuestras unidades móviles ofrecen: consultas médicas generales, vacunación, control prenatal y pediátrico, detección temprana de enfermedades y educación sanitaria.";
            } else if (this.textContent === "Horarios") {
                tabText.textContent = "Las unidades visitan cada comunidad según un calendario establecido. Contacta con nosotros para conocer el calendario de visitas a tu localidad.";
            }
        });
    });

    // Animaciones para elementos interactivos
    const interactiveCards = document.querySelectorAll(".interactive-card, .telemedicina-card");
    interactiveCards.forEach(card => {
        card.addEventListener("mouseenter", function() {
            this.style.transform = "translateY(-5px)";
            this.style.boxShadow = "0 10px 20px rgba(0,0,0,0.1)";
        });
        card.addEventListener("mouseleave", function() {
            this.style.transform = "translateY(0)";
            this.style.boxShadow = "var(--shadow-2)";
        });
    });
});