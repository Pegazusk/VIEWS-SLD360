validarSesion();

$(window).on('load', function() {
    validarSesion();

    let form = new FormData();

    let estadoCivilPromise = peticion('catalogos', 'dameEstadoCivil', form).then(rsl => rsl.json());
    let escolaridadPromise = peticion('catalogos', 'dameEscolaridad', form).then(rsl => rsl.json());
    let tiposSanguineosPromise = peticion('catalogos', 'dameTipoSangre', form).then(rsl => rsl.json());

    Promise.all([estadoCivilPromise, escolaridadPromise, tiposSanguineosPromise]).then(resultados => {
        let resultadoEstados = resultados[0];
        let resultadoEscolaridades = resultados[1];
        let resultadoTipoSangre = resultados[2];

        if (!resultadoEstados.Error) {
            let estados = resultadoEstados.Estados;
            estados.forEach(estado => {
                $('#cmbEstadoCivil').append(`<option value="${estado.id}">${estado.nombre}</option>`);
            });
        }

        if (!resultadoEscolaridades.Error) {
            let escolaridades = resultadoEscolaridades.Escolaridades;
            escolaridades.forEach(escolaridad => {
                $('#cmbEscolaridad').append(`<option value="${escolaridad.id}">${escolaridad.nombre}</option>`);
            });
        }

        if (!resultadoTipoSangre.Error) {
            let tiposSanguineos = resultadoTipoSangre.TiposSanguineos;
            tiposSanguineos.forEach(tipoSanguineo => {
                $('#cmbTipoSanguineo').append(`<option value="${tipoSanguineo.id}">${tipoSanguineo.nombre}</option>`);
            });
        }

        cargaInformacion();
    }).catch(error => {
        console.error('Error al cargar los catálogos:', error);
    });
});

function cargaInformacion() {
    validarSesion();

    let form = new FormData();
    form.append('idUsuario', localStorage.getItem('idUsuario'));

    peticion('pacientes', 'dameGeneralesXId', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
            window.location = dameUrlServidor(true) + 'portal/index.html';
        } else {
            $('.txtNombrePersona').text(resultado.nombrePersona);


            $('#txtCurp').val(resultado.curpPersona);
            $('#txtNacimiento').val(resultado.nacimientoPersona);
            $('#cmbEstadoCivil').val(resultado.estadoCivilPersona);
            $('#cmbEscolaridad').val(resultado.escolaridadPersona);
            $('#cmbSexo').val(resultado.sexoPersona);
            $('#cmbEsIndigena').val(resultado.esIndigenaPersona);
            $('#txtTelefono').val(resultado.telefonoPersona);

            $('#txtCalleYNumero').val(resultado.calleYNumeroPersona);
            $('#txtColonia').val(resultado.coloniaPersona);
            $('#txtMunicipio').val(resultado.municipioPersona);
            $('#txtCodigoPostal').val(resultado.postalPersona);
            $('#txtEntidadFederativa').val(resultado.entidadFederativaPersona);

            $('#txtEmpresa').val(resultado.empresaPersona);
            $('#txtOcupacion').val(resultado.ocupacionPersona);
            $('#txtSector').val(resultado.sectorPersona);
            $('#txtRiesgos').val(resultado.riesgosPersona);

            $('.chkDiabetes').prop('checked', resultado.diabetesPersona == 1);
            $('.chkHipertension').prop('checked', resultado.hipertensionPersona == 1);
            $('.chkCancer').prop('checked', resultado.cancerPersona == 1);
            $('.chkEmbarazo').prop('checked', resultado.embarazoPersona == 1);
            $('.chkAnticonceptivo').prop('checked', resultado.anticonceptivoPersona == 1);
        }
    });
}

function guardaDatosGenerales() {
    validarSesion();

    let form = new FormData();
    let curp = $('#txtCurp').val();
    let nacimiento = $('#txtNacimiento').val();
    let estadoCivil = $('#cmbEstadoCivil :selected').val();
    let escolaridad = $('#cmbEscolaridad :selected').val();
    let esIndigena = $('#cmbEsIndigena :selected').val();
    let sexo = $('#cmbSexo :selected').val();
    let telefono = $('#txtTelefono').val();

    let calleynumero = $('#txtCalleYNumero').val();
    let colonia = $('#txtColonia').val();
    let municipio = $('#txtMunicipio').val();
    let postal = $('#txtCodigoPostal').val();
    let entidadFederativa = $('#txtEntidadFederativa').val();

    if (curp == '' || nacimiento == '' || telefono == '' || estadoCivil == '' || escolaridad == '' || sexo == '' || calleynumero == '' || colonia == '' || municipio == '' || postal == '' || entidadFederativa == '') {
        alert('Todos los campos generales deben ser llenados');
        return;
    }


    form.append('idUsuario', localStorage.getItem('idUsuario'));
    form.append('curp',  curp);
    form.append('nacimiento', nacimiento);
    form.append('estadoCivil', estadoCivil);
    form.append('escolaridad', escolaridad);
    form.append('sexo', sexo);
    form.append('telefono',  telefono);
    form.append('esIndigena', esIndigena);
    form.append('calleynumero', calleynumero);
    form.append('colonia', colonia);
    form.append('municipio', municipio);
    form.append('postal', postal);
    form.append('entidadfederativa', entidadFederativa);

    peticion('pacientes', 'actualizaInformacionGeneral', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
        } else {
            cargaInformacion();
        }
    });
}

function guardaDatosEmpleo() {
    validarSesion();

    let form = new FormData();
    let empresa = $('#txtEmpresa').val();
    let ocupacion = $('#txtOcupacion').val();
    let sector = $('#txtSector').val();
    let riesgos = $('#txtRiesgos').val();

    if (empresa == '' || ocupacion == '' || sector == '' || riesgos == '') {
        alert('Todos los campos de empleo deben ser llenados');
        return;
    }


    form.append('idUsuario', localStorage.getItem('idUsuario'));
    form.append('empresa',  empresa);
    form.append('ocupacion', ocupacion);
    form.append('sector', sector);
    form.append('riesgos', riesgos);

    peticion('pacientes', 'actualizaInformacionEmpleo', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
        } else {
            cargaInformacion();
        }
    });
}

function guardaDatosMedicos() {
    validarSesion();

    let form = new FormData();
    let isDiabetes = $('.chkDiabetes').prop('checked') ? 1 : 0;
    let isHipertension = $('.chkHipertension').prop('checked') ? 1 : 0;
    let isCancer = $('.chkCancer').prop('checked') ? 1 : 0;
    let isEmbarazo = $('.chkEmbarazo').prop('checked') ? 1 : 0;
    let isAnticonceptivo = $(".chkAnticonceptivo").prop('checked') ? 1 : 0;

    form.append('idUsuario', localStorage.getItem('idUsuario'));
    form.append('diabetes',  isDiabetes);
    form.append('hipertension', isHipertension);
    form.append('cancer', isCancer);
    form.append('embarazo', isEmbarazo);
    form.append('anticonceptivo', isAnticonceptivo);

    peticion('pacientes', 'actualizaInformacionMedica', form).then(rsl => rsl.json()).then(resultado => {
        if (resultado.Error) {
            alert(resultado.ErrorMensaje);
        } else {
            cargaInformacion();
        }
    });
}