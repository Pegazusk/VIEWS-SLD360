<?php

class DoctorService extends Service
{

    public function dameGeneralesXId()
    {
        $idUsuario = $_POST['idUsuario'];

        $result = $this->conexion->consulta("CALL sp_doctores_generales($idUsuario);");
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

    public function damePacientes() {
        $idUsuario = $_POST['idUsuario'];

        $result = $this->conexion->consulta("CALL sp_doctores_damePacientes($idUsuario);");

        $pacientes = [];
        while ($paciente = $this->conexion->fila($result)) {
            $pacientes[] = $paciente;
        }

        return ['Error' => false, 'ErrorMensaje' => '', 'Pacientes' => $pacientes];
    }

    public function dameCitasXId()
    {
        $idUsuario = $_POST['idUsuario'];

        $result = $this->conexion->consulta("CALL sp_doctor_citas($idUsuario);");
        if (!$result) {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error'];
        }

        $citas = [];
        while ($cita = $this->conexion->fila($result)) {
            $citas[] = $cita;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Citas' => $citas]);

    }

    public function registraCita()
    {
        $idEspecialidad = $_POST['idEspecialidad'];
        $idPaciente = $_POST['idPaciente'];
        $fecha = $_POST['fecha'];
        $consultorio = $_POST['idConsultorio'];
        $idUsuario = $_POST['idUsuario'];

        $fecha = date('Y-m-d H:i:s', strtotime($fecha));

        $this->conexion->consulta("CALL sp_doctor_registraCita($idPaciente, $idEspecialidad, $idUsuario, '$fecha', $consultorio);");

        return ['Error' => false, 'ErrorMensaje' => ''];
    }

    public function dameIdPersona($idUsuario)
    {
        $result = $this->conexion->consulta("SELECT * FROM persona WHERE fk_usuario = $idUsuario");

        $resultado = $this->conexion->fila($result);

        return $resultado['id'];
    }

    public function dameIdDoctor($idUsuario)
    {
        $result = $this->conexion->consulta("SELECT * FROM doctor WHERE fk_persona = " . $this->dameIdPersona($idUsuario));

        $resultado = $this->conexion->fila($result);

        return $resultado['id'];
    }

}