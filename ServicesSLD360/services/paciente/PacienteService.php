<?php

class PacienteService extends Service
{

    public function dameGeneralesXId()
    {
        $idUsuario = $_POST['idUsuario'];

        $result = $this->conexion->consulta("CALL sp_pacientes_generales($idUsuario);");
        $fila = $this->conexion->fila($result);

        if (!$fila) {
            return ['Error' => true, 'ErrorMensaje' => 'No se encontró ningún usuario con los filtros seleccionados.'];
        }

        if (count($fila) > 0) {
            return array_merge(['Error' => false, 'ErrorMensaje' => ''], $fila);
        } else {
            return ['Error' => true, 'ErrorMensaje' => 'No se encontró ningún usuario con los filtros seleccionados.'];
        }
    }

    public function dameEmpeloXId()
    {
        $idUsuario = $_POST['idUsuario'];
        $idPersona = $_POST['idPersona'];

        $result = $this->conexion->consulta("SELECT * FROM persona_empleo WHERE fk_paciente = " . $this->dameIdPaciente($_POST['idUsuario']));
        $fila = $this->conexion->fila($result);

        if (!$fila) {
            return ['Error' => true, 'ErrorMensaje' => 'No se encontró ningún empleo con los filtros seleccionados.'];
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], $fila);
    }

    public function dameCitasXId()
    {
        $idUsuario = $_POST['idUsuario'];

        $result = $this->conexion->consulta("CALL sp_pacientes_citas($idUsuario);");
        if (!$result) {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error'];
        }

        $citas = [];
        while ($cita = $this->conexion->fila($result)) {
            $citas[] = $cita;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Citas' => $citas]);

    }

    public function dameVacunasXId()
    {
        $idUsuario = $_POST['idUsuario'];
        $idVacuna = $_POST['idVacuna'];

        $result = $this->conexion->consulta("SELECT * FROM paciente_vacuna WHERE fk_vacuna = $idVacuna AND fk_paciente = ".$this->dameIdPaciente($idUsuario));
        if (!$result) {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error'];
        }

        $vacunas = [];
        while ($vacuna = $this->conexion->fila($result)) {
            $vacunas[] = $vacuna;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Vacunas' => $vacunas]);

    }

    public function actualizaInformacionGeneral()
    {
        $curp = $_POST['curp'];
        $nacimiento = $_POST['nacimiento'];
        $estadoCivil = $_POST['estadoCivil'];
        $escolaridad = $_POST['escolaridad'];
        $sexo = $_POST['sexo'];
        $telefono = $_POST['telefono'];
        $esIndigena = $_POST['esIndigena'];

        $calleynumero = $_POST['calleynumero'];
        $colonia = $_POST['colonia'];
        $municipio = $_POST['municipio'];
        $postal = $_POST['postal'];
        $entidadfederativa = $_POST['entidadfederativa'];

        $nacimiento = date("Y-m-d", strtotime($nacimiento));


        $idPersona = $this->dameIdPersona($_POST['idUsuario']);

        $result = $this->conexion->consulta("UPDATE persona SET curp = '$curp', fecha_nacimiento = '$nacimiento', fk_estado_civil = $estadoCivil, fk_escolaridad = $escolaridad, sexo = '$sexo', telefono = $telefono, esIndigena = $esIndigena, calleynumero='$calleynumero', colonia='$colonia', municipio='$municipio', postal='$postal', entidad='$entidadfederativa' WHERE id = '$idPersona'");
        if ($result) {
            return ['Error' => false, 'ErrorMensaje' => ''];
        } else {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error al intentar actualizar los datos.'];
        }
    }

    public function actualizaInformacionEmpleo()
    {
        $empresa = $_POST['empresa'];
        $ocupacion = $_POST['ocupacion'];
        $sector = $_POST['sector'];
        $riesgos = $_POST['riesgos'];

        $result = $this->conexion->consulta("UPDATE paciente_empleo SET empresa = '$empresa', ocupacion='$ocupacion', sector='$sector', riesgos='$riesgos' WHERE fk_paciente = " . $this->dameIdPaciente($_POST['idUsuario']));
        if ($result) {
            return ['Error' => false, 'ErrorMensaje' => ''];
        } else {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error al intentar actualizar los datos.'];
        }
    }

    public function actualizaInformacionMedica()
    {
        $diabetes = $_POST['diabetes'];
        $hipertension = $_POST['hipertension'];
        $cancer = $_POST['cancer'];
        $embarazo = $_POST['embarazo'];
        $anticonceptivo = $_POST['anticonceptivo'];

        $result = $this->conexion->consulta("UPDATE paciente_salud SET is_diabetes = '$diabetes', is_hipertension='$hipertension', is_cancer='$cancer', is_embarazo='$embarazo', is_anticonceptivo = '$anticonceptivo' WHERE fk_paciente = " . $this->dameIdPaciente($_POST['idUsuario']));
        if ($result) {
            return ['Error' => false, 'ErrorMensaje' => ''];
        } else {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error al intentar actualizar los datos.'];
        }
    }

    public function registrarDosisVacuna()
    {
        $idUsuario = $_POST['idUsuario'];
        $idVacuna = $_POST['idVacuna'];
        $fecha = $_POST['fecha'];
        $lote = $_POST['lote'];

        $fecha = date("Y-m-d", strtotime($fecha));


        $result = $this->conexion->consulta("INSERT INTO paciente_vacuna VALUES (" . $this->dameIdPaciente($idUsuario) . ", $idVacuna, '$fecha', '$lote', 1)");
        if (!$result) {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error'];
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => '']);
    }

    public function dameIdPersona($idUsuario)
    {
        $result = $this->conexion->consulta("SELECT * FROM persona WHERE fk_usuario = " . $idUsuario);

        $resultado = $this->conexion->fila($result);

        return $resultado['id'];
    }

    public function dameIdPaciente($idUsuario)
    {
        $result = $this->conexion->consulta("SELECT * FROM paciente WHERE fk_persona = " . $this->dameIdPersona($idUsuario));

        $resultado = $this->conexion->fila($result);

        return $resultado['id'];
    }


}