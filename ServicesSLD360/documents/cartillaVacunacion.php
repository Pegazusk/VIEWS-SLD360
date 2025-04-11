<?php
error_reporting(E_ALL ^ E_WARNING ^ E_NOTICE);
ini_set('display_errors', '1');

// Configuración de la base de datos (usar la misma que en historialclinico.php)
$host = 'localhost:3306';
$user = 'root';
$pass = 'Gtagomez1';
$db = 'SLD360';

// Conexión a MySQLi
$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

// Obtener ID de receta desde la URL
$idReceta = isset($_GET['id']) ? (int)$_GET['id'] : 0;

if ($idReceta <= 0) {
    die("ID de receta no válido");
}

// 1. Obtener información de la receta
// 1. Obtener información de la receta
$result = $conn->query("
    SELECT 
        r.*, 
        CONCAT_WS(' ', p.nombre, p.apellido_uno, p.apellido_dos) as nombre_paciente,
        TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) as edad,
        CONCAT_WS(' ', d.nombre, d.apellido_uno, d.apellido_dos) as nombre_doctor,
        doc.cedula as cedula
    FROM receta r
    JOIN paciente pa ON r.fk_paciente = pa.id
    JOIN persona p ON pa.fk_persona = p.id
    JOIN doctor doc ON r.fk_doctor = doc.id
    JOIN persona d ON doc.fk_persona = d.id
    WHERE r.id = $idReceta
");

if (!$result) {
    die("Error en la consulta: " . $conn->error);
}

if ($result->num_rows === 0) {
    die("No se encontró la receta con ID $idReceta");
}

$receta = $result->fetch_assoc();
$result->close();

// 2. Obtener medicamentos de la receta
$result = $conn->query("
    SELECT * FROM receta_medicamento 
    WHERE fk_receta = $idReceta
    ORDER BY id
");
$medicamentos = $result->fetch_all(MYSQLI_ASSOC);
$result->close();

require('../libs/fpdf/fpdf.php');

class PDF extends FPDF
{
    private $title = 'RECETA MÉDICA';

    function Header()
    {
        $this->Image('../img/logo-sld.png', 15, 10, 15);

        $this->SetFont('Arial', 'B', 16);
        $this->Cell(0, 10, mb_convert_encoding($this->title, 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');
        $this->SetFont('Arial', '', 10);
        $this->Cell(0, 5, mb_convert_encoding('SLD360', 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');

        // Línea separadora
        $this->Line(15, 30, 195, 30);
        $this->Ln(15);
    }

    function Footer()
    {
        $this->SetY(-15);
        $this->SetFont('Arial', 'I', 8);
        $this->Cell(0, 10, 'Página ' . $this->PageNo() . '/{nb}', 0, 0, 'C');
    }

    function InfoPaciente($receta)
    {
        $this->SetFont('Arial', '', 10);
        
        // Columna izquierda
        $this->Cell(60, 6, 'Paciente:', 0, 0);
        $this->Cell(0, 6, mb_convert_encoding($receta['nombre_paciente'], 'ISO-8859-1', 'UTF-8'), 0, 1);
        
        $this->Cell(60, 6, 'Fecha:', 0, 0);
        $this->Cell(0, 6, date('d/m/Y', strtotime($receta['fecha'])), 0, 1);
        
        // Columna derecha
        $this->Cell(60, 6, 'Edad:', 0, 0);
        $this->Cell(0, 6, $receta['edad'] . ' anios', 0, 1);
        
        $this->Ln(10);
    }

    function Diagnostico($texto)
    {
        $this->SetFont('Arial', 'B', 11);
        $this->Cell(0, 6, 'DIAGNOSTICO:', 0, 1);
        $this->SetFont('Arial', '', 10);
        $this->MultiCell(0, 6, mb_convert_encoding($texto, 'ISO-8859-1', 'UTF-8'));
        $this->Ln(8);
    }

    function Medicamentos($medicamentos)
    {
        $this->SetFont('Arial', 'B', 11);
        $this->Cell(0, 6, 'TRATAMIENTO:', 0, 1);
        $this->SetFont('Arial', '', 10);
        
        // Cabecera de tabla
        $this->SetFillColor(230, 230, 230);
        $this->Cell(70, 7, 'Medicamento', 1, 0, 'C', true);
        $this->Cell(30, 7, 'Presentacion', 1, 0, 'C', true);
        $this->Cell(30, 7, 'Dosis', 1, 0, 'C', true);
        $this->Cell(30, 7, 'Frecuencia', 1, 0, 'C', true);
        $this->Cell(30, 7, 'Duracion', 1, 1, 'C', true);
        
        // Contenido
        $this->SetFillColor(255, 255, 255);
        foreach ($medicamentos as $med) {
            $this->Cell(70, 7, mb_convert_encoding($med['medicamento'], 'ISO-8859-1', 'UTF-8'), 1);
            $this->Cell(30, 7, mb_convert_encoding($med['presentacion'], 'ISO-8859-1', 'UTF-8'), 1);
            $this->Cell(30, 7, mb_convert_encoding($med['dosis'], 'ISO-8859-1', 'UTF-8'), 1);
            $this->Cell(30, 7, mb_convert_encoding($med['frecuencia'], 'ISO-8859-1', 'UTF-8'), 1);
            $this->Cell(30, 7, mb_convert_encoding($med['duracion'], 'ISO-8859-1', 'UTF-8'), 1, 1);
        }
        
        $this->Ln(10);
    }

    function Indicaciones($texto)
    {
        $this->SetFont('Arial', 'B', 11);
        $this->Cell(0, 6, 'INDICACIONES ADICIONALES:', 0, 1);
        $this->SetFont('Arial', '', 10);
        $this->MultiCell(0, 6, mb_convert_encoding($texto, 'ISO-8859-1', 'UTF-8'));
        $this->Ln(15);
    }

    function Firma($doctor, $cedula)
    {
        $this->Cell(0, 6, '_________________________________________', 0, 1, 'C');
        $this->SetFont('Arial', 'B', 10);
        $this->Cell(0, 6, mb_convert_encoding('Dr. ' . $doctor, 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');
        $this->Cell(0, 6, 'Cedula Profesional: ' . $cedula, 0, 1, 'C');
    }
}

// Crear PDF
$pdf = new PDF();
$pdf->AliasNbPages();
$pdf->AddPage();

// Información del paciente
$pdf->InfoPaciente($receta);

// Diagnóstico
$pdf->Diagnostico($receta['diagnostico']);

// Medicamentos
$pdf->Medicamentos($medicamentos);

// Indicaciones adicionales
if (!empty($receta['indicaciones'])) {
    $pdf->Indicaciones($receta['indicaciones']);
}

// Firma del doctor
$pdf->Firma($receta['nombre_doctor'], $receta['cedula']);

$pdf->Output('I', 'Receta_' . $receta['nombre_paciente'] . '.pdf');

$conn->close();
?>