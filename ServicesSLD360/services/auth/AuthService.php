<?php

class AuthService extends Service
{

    public function login()
    {
        $nick = $_POST['correo'];
        $clave = md5($_POST['clave']);

        $result = $this->conexion->consulta("SELECT * FROM usuario WHERE nick = '$nick' AND clave = '$clave' AND estatus = 1");
        $fila = $this->conexion->fila($result);

        if (!$fila) {
            return ['Error' => true, 'ErrorMensaje' => 'No se encontró ningún usuario con los filtros seleccionados.'];
        }

        if (count($fila) > 0) {
            return ['Error' => false, 'ErrorMensaje' => '', 'idUsuario' => $fila['id'], 'idRol' => $fila['fk_rol']];
        } else {
            return ['Error' => true, 'ErrorMensaje' => 'No se encontró ningún usuario con los filtros seleccionados.'];
        }
    }

    public function registro()
    {
        $nombre = $_POST['nombre'];
        $apellido_uno = $_POST['apellidoUno'];
        $apellido_dos = $_POST['apellidoDos'];
        $correo = $_POST['correo'];
        $clave = md5($_POST['clave']);

        $result = $this->conexion->consulta("CALL sp_usuarios_registro(1, '$nombre', '$apellido_uno', '$apellido_dos', '$correo', '$clave')");
        $fila = $this->conexion->fila($result);

        if (!$fila) {
            return ['Error' => true, 'ErrorMensaje' => 'Ocurrió un error al intentar registrar el usuario'];
        }

        return ['Error' => false, 'ErrorMensaje' => '', 'idUsuario' => $fila['idUsuario']];
    }

}