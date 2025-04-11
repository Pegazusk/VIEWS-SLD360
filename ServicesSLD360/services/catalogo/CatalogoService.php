<?php

class CatalogoService extends Service
{

    public function dameEstadoCivil()
    {
        $resultado = $this->conexion->consulta("SELECT * FROM persona_cat_estado_civil WHERE estatus = 1");

        $estados = [];
        while ($cita = $this->conexion->fila($resultado)) {
            $estados[] = $cita;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Estados' => $estados]);
    }

    public function dameEscolaridad()
    {
        $resultado = $this->conexion->consulta("SELECT * FROM persona_cat_escolaridad WHERE estatus = 1");

        $escolaridades = [];
        while ($escolaridad = $this->conexion->fila($resultado)) {
            $escolaridades[] = $escolaridad;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Escolaridades' => $escolaridades]);
    }

    public function dameTipoSangre()
    {
        $resultado = $this->conexion->consulta("SELECT * FROM persona_cat_tipo_sangre WHERE estatus = 1");

        $tipos = [];
        while ($tipo = $this->conexion->fila($resultado)) {
            $tipos[] = $tipo;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['TiposSanguineos' => $tipos]);
    }

    public function dameEspecialidad()
    {
        $idUsuario = $_POST['idUsuario'];
        $resultado = $this->conexion->consulta("select * from doctor_especialidad de inner join especialidad e on de.fk_especialidad = e.id where estatus=1 and fk_doctor = (select id from doctor where fk_persona = (select id from persona where fk_usuario = $idUsuario));");

        $especialidades = [];
        while ($especialidad = $this->conexion->fila($resultado)) {
            $especialidades[] = $especialidad;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Especialidades' => $especialidades]);
    }

    public function dameConsultorio()
    {
        $idUsuario = $_POST['idUsuario'];
        $resultado = $this->conexion->consulta("select * from doctor_consultorio dc inner join consultorio c on dc.fk_consultorio = c.id  where estatus=1 and fk_doctor = (select id from doctor where fk_persona = (select id from persona where fk_usuario = $idUsuario));");

        $consultorios = [];
        while ($consultorio = $this->conexion->fila($resultado)) {
            $consultorios[] = $consultorio;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Consultorios' => $consultorios]);
    }

    public function damePacientes()
    {
        $resultado = $this->conexion->consulta("select * from paciente inner join persona on paciente.fk_persona = persona.id where paciente.estatus=1;");

        $pacientes = [];
        while ($paciente = $this->conexion->fila($resultado)) {
            $pacientes[] = $paciente;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Pacientes' => $pacientes]);
    }

    public function dameVacunas()
    {
        $resultado = $this->conexion->consulta("select * from paciente_cat_vacuna WHERE estatus = 1;");

        $vacunas = [];
        while ($vacuna = $this->conexion->fila($resultado)) {
            $vacunas[] = $vacuna;
        }

        return array_merge(['Error' => false, 'ErrorMensaje' => ''], ['Vacunas' => $vacunas]);
    }


}