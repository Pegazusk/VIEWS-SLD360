<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);

require_once('headers.php');
require_once('conexion.php');
require_once('services/Service.php');
require_once('services/auth/AuthService.php');
require_once('services/paciente/PacienteService.php');
require_once('services/catalogo/CatalogoService.php');
require_once('services/doctor/DoctorService.php');
require_once('services/bot/BotService.php');
require_once('services/receta/RecetaService.php');
require_once('services/cartilla/CartillaService.php');

$servicio = $_POST['servicio'];
if ($servicio == '') die('No se especificó una ruta para establecer la comunicación con el servicio.');

$funcion = $_POST['funcion'];
if ($funcion == '') die('No se especificó la ruta del endpoint para controlar la comunicación con el servicio.');

$conexion = new Conexion();

$servicioClass = null;
switch($servicio) {
    case 'auth':
        $servicioClass = new AuthService($conexion);
        break;

    case 'pacientes':
        $servicioClass = new PacienteService($conexion);
        break;

    case 'catalogos':
        $servicioClass = new CatalogoService($conexion);
        break;

    case 'doctores':
        $servicioClass = new DoctorService($conexion);
        break;

    case 'bot':
        $servicioClass = new BotService($conexion);
        break;

    case 'receta':
        $servicioClass = new RecetaService($conexion);
         break;

    case 'cartilla':
        $servicioClass = new CartillaService($conexion);
        break;

    default:
        echo json_encode(['Error' => true, 'ErrorMensaje' => 'No se encontró el servicio ' . $servicio . '.']);
    break;
}

if ($servicioClass instanceof Service) {
    $servicioClass->llamar($funcion);
}