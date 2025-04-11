<?php

class Service {

    protected $conexion;

    public function __construct(Conexion $conexion) {
        $this->conexion = $conexion;
    }

    public function llamar($funcion) {
        if (method_exists($this, $funcion)) {
            $resultado = $this->$funcion();
            if (is_array($resultado)) {
                if (isset($resultado['Error']) && isset($resultado['ErrorMensaje'])) {
                    echo json_encode($resultado);
                } else {
                    echo json_encode(['Error' => true, 'ErrorMensaje' => 'El arreglo de retorno no contiene los registros de error ni del error detalle.']);
                }
            } else {
                echo json_encode(['Error' => true, 'ErrorMensaje' => 'El retorno de la funcion ' . $funcion . ' no es un arreglo.']);
            }
        } else {
            throw new Exception("La función " . $funcion . " no existe en la clase.");
        }
    }
}