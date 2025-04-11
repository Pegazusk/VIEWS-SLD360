document.addEventListener("DOMContentLoaded", function () {
    const etsData = [
      { nombre: "VIH/SIDA", sintomas: "Fiebre, fatiga, dolor de garganta, pérdida de peso, ganglios linfáticos inflamados.",
        prevencion: "Uso correcto de preservativos, no compartir agujas, PrEP (profilaxis preexposición), circuncisión masculina.",
        tratamiento: "Terapia antirretroviral (TAR) que combina varios medicamentos para controlar el virus." },

      { nombre: "Sífilis", sintomas: "Etapa 1: Llagas indoloras (chancros). Etapa 2: Erupción cutánea, fiebre, dolor de cabeza. Etapas tardías: Daño a órganos internos.",
        prevencion: "Uso consistente de preservativos, pruebas de detección regulares, tratamiento temprano.",
        tratamiento: "Penicilina inyectable. En alérgicos, se usan otros antibióticos como doxiciclina." },

      { nombre: "Gonorrea", sintomas: "Dolor al orinar, secreción anormal (blanca, amarilla o verde), dolor pélvico en mujeres, dolor testicular en hombres.",
        prevencion: "Uso correcto de preservativos, limitación de parejas sexuales, pruebas periódicas.",
        tratamiento: "Combinación de ceftriaxona (inyección) y azitromicina (oral) debido a resistencia antibiótica." },

      { nombre: "Clamidia", sintomas: "Muchas personas son asintomáticas. Cuando hay síntomas: secreción anormal, dolor al orinar, dolor durante relaciones, dolor testicular.",
        prevencion: "Uso consistente de preservativos, pruebas anuales para mujeres sexualmente activas menores de 25 años.",
        tratamiento: "Azitromicina (dosis única) o doxiciclina (7 días). Tratar a todas las parejas sexuales." },

      { nombre: "Herpes Genital", sintomas: "Ampollas dolorosas, picazón, úlceras en genitales, recto o boca. Puede causar síntomas similares a la gripe en el primer brote.",
        prevencion: "Uso de preservativos (reduce pero no elimina riesgo), evitar contacto durante brotes, medicamentos antivirales diarios para reducir transmisión.",
        tratamiento: "Antivirales como aciclovir, valaciclovir o famciclovir para tratar brotes y reducir recurrencias." },

      { nombre: "VPH (Virus del Papiloma Humano)", sintomas: "Muchas cepas son asintomáticas. Algunas causan verrugas genitales. Cepas de alto riesgo pueden causar cáncer cervical, anal u orofaríngeo.",
        prevencion: "Vacunación (recomendada para preadolescentes), uso de preservativos (protección parcial), pruebas de Papanicolaou regulares.",
        tratamiento: "No existe tratamiento para el virus mismo. Se tratan las verrugas con medicamentos tópicos o procedimientos. Lesiones precancerosas requieren seguimiento médico." }
    ];

    const etsListContainer = document.getElementById("ets-list");

    etsData.forEach(ets => {
      const card = document.createElement("div");
      card.className = "card";
      card.innerHTML = `
        <div class="card-body">
          <h3 class="card-title">${ets.nombre}</h3>
          <div class="card-section">
            <h4 class="card-subtitle">Síntomas:</h4>
            <p class="card-text">${ets.sintomas}</p>
          </div>
          <div class="card-section">
            <h4 class="card-subtitle">Prevención:</h4>
            <p class="card-text">${ets.prevencion}</p>
          </div>
          <div class="card-section">
            <h4 class="card-subtitle">Tratamiento:</h4>
            <p class="card-text">${ets.tratamiento}</p>
          </div>
        </div>
      `;
      etsListContainer.appendChild(card);
    });
  });