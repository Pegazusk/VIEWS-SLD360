<?php

class Conexion {

    private $mysql = [
        'servidor' => 'localhost:3306',
        'usuario' => 'root',
        'clave' => 'Gtagomez1'
    ];

    protected $conexion;

    public function __construct() {
        $this->conexion = mysqli_connect($this->mysql['servidor'], $this->mysql['usuario'], $this->mysql['clave'], 'SLD360');

        if (!$this->conexion) {
            echo json_encode(['Error' => true, 'ErrorMensaje' => 'No es posible establecer conexión con el servidor de MySQL.']);
            die();
        }
    }

    public function dameConexion() {
        return $this->conexion;
    }

    public function consulta($sql) {
        $result = $this->dameConexion()->query($sql);

        if (!$result) {
            echo json_encode(['Error' => true, 'ErrorMensaje' => 'Ocurrió un error al intentar ejecutar la consulta ' . $sql]);
            die();
        }

        return $result;
    }

    public function fila($result) {
        return mysqli_fetch_assoc($result);
    }

    public function escape($valor) {
        return mysqli_real_escape_string($this->conexion, $valor);
    }

    public function obtenerUltimoId() {
        return $this->conexion->insert_id;
    }
}