<?php

error_reporting(E_ALL ^ E_WARNING ^ E_NOTICE);
ini_set('display_errors', '1');

// Configuración de la base de datos
$host = 'localhost:3306';
$user = 'root';
$pass = 'Gtagomez1';
$db = 'SLD360';

// Conexión a MySQLi
$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

// Obtener ID del usuario desde la URL
$idUsuario = isset($_GET['id']) ? (int)$_GET['id'] : 0;

if ($idUsuario <= 0) {
    die("ID de usuario no válido");
}

// 1. Obtener información básica del paciente
$result = $conn->query("CALL sp_pacientes_generales($idUsuario)");
$paciente = $result->fetch_assoc();
$result->close();
$conn->next_result();

// 2. Obtener información personal adicional
$result = $conn->query("
    SELECT p.*, ec.nombre as estado_civil, es.nombre as escolaridad 
    FROM persona p
    LEFT JOIN persona_cat_estado_civil ec ON p.fk_estado_civil = ec.id
    LEFT JOIN persona_cat_escolaridad es ON p.fk_escolaridad = es.id
    WHERE p.fk_usuario = $idUsuario
");
$infoPersona = $result->fetch_assoc();
$result->close();

// 3. Obtener ID del paciente
$result = $conn->query("SELECT id FROM paciente WHERE fk_persona = (SELECT id FROM persona WHERE fk_usuario = $idUsuario)");
$idPaciente = $result->fetch_row()[0];
$result->close();

// 4. Obtener información de salud
$result = $conn->query("SELECT * FROM paciente_salud WHERE fk_paciente = $idPaciente");
$salud = $result->fetch_assoc();
$result->close();

// 5. Obtener citas del paciente
$result = $conn->query("
    SELECT c.id, c.fecha_cita, e.nombre as especialidad, 
           CONCAT_WS(' ', per.nombre, per.apellido_uno, per.apellido_dos) as doctor,
           con.nombre as consultorio, cd.padecimiento
    FROM cita c
    JOIN especialidad e ON c.fk_especialidad = e.id
    JOIN doctor d ON c.fk_doctor = d.id
    JOIN persona per ON d.fk_persona = per.id
    JOIN consultorio con ON c.fk_consultorio = con.id
    LEFT JOIN cita_detalle cd ON c.id = cd.fk_cita
    WHERE c.fk_paciente = $idPaciente
    ORDER BY c.fecha_cita DESC
");
$citas = $result->fetch_all(MYSQLI_ASSOC);
$result->close();

require('../libs/fpdf/fpdf.php');

class PDF extends FPDF
{
    private $title = 'HISTORIAL CLÍNICO';

    function Header()
    {
        $this->Image('../img/logo-sld.png', 15, 10, 15);

        $this->SetFont('Arial', 'B', 16);
        $this->Cell(0, 10, mb_convert_encoding($this->title, 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');
        $this->SetFont('Arial', '', 10);
        $this->Cell(0, 5, mb_convert_encoding('SLD360 - Centro Médico Especializado', 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');

        // Línea separadora
        $this->Line(15, 30, 195, 30);
        $this->Ln(15);
    }

    function Footer()
    {
        $this->SetY(-15);
        $this->SetFont('Arial', 'I', 8);
        $this->Cell(0, 10, 'Página ' . $this->PageNo() . '/{nb}', 0, 0, 'C');
        $this->Cell(0, 10, 'Documento confidencial - Generado el ' . date('d/m/Y'), 0, 0, 'R');
    }

    function SectionTitle($title)
    {
        $this->SetFont('Arial', 'B', 12);
        $this->SetFillColor(230, 230, 230);
        $this->Cell(0, 8, $title, 0, 1, 'L', true);
        $this->Ln(4);
    }

    function LabelValue($label, $value, $width = 50)
    {
        $this->SetFont('Arial', 'B', 10);
        $this->Cell($width, 6, $label . ':', 0, 0);
        $this->SetFont('Arial', '', 10);
        $this->Cell(0, 6, $value, 0, 1);
    }

    function Checkbox($label, $checked, $x, $y, $size = 4)
    {
        $this->Rect($x, $y, $size, $size);
        if ($checked) {
            $this->Line($x, $y, $x + $size, $y + $size);
            $this->Line($x, $y + $size, $x + $size, $y);
        }
        $this->SetXY($x + $size + 2, $y);
        $this->Cell(0, $size, $label);
    }
}

// Crear PDF
$pdf = new PDF();
$pdf->AliasNbPages();
$pdf->AddPage();

$pdf->SectionTitle('DATOS DEL PACIENTE');

$pdf->LabelValue('Nombre completo', $paciente['nombrePersona']);
$pdf->LabelValue('CURP', $paciente['curpPersona'] ?? 'No registrada');
$pdf->LabelValue('Fecha de nacimiento', $paciente['nacimientoPersona'] ?? 'No registrada');


if ($paciente['nacimientoPersona']) {
    $edad = date_diff(date_create($paciente['nacimientoPersona']), date_create('today'))->y;
    $pdf->LabelValue('Edad', mb_convert_encoding($edad . ' años', 'ISO-8859-1', 'UTF-8'));
}

$pdf->LabelValue('Sexo', ($infoPersona['sexo'] == 'H') ? 'Masculino' :
    (($infoPersona['sexo'] == 'M') ? 'Femenino' : 'No especificado'));
$pdf->LabelValue('Estado civil', $infoPersona['estado_civil'] ?? 'No registrado');
$pdf->LabelValue(mb_convert_encoding('Teléfono', 'ISO-8859-1', 'UTF-8'), $infoPersona['telefono'] ?? 'No registrado');
$pdf->LabelValue(mb_convert_encoding('Correo electrónico', 'ISO-8859-1', 'UTF-8'), $infoPersona['correo'] ?? 'No registrado');
$pdf->Ln(10);


$pdf->SectionTitle(mb_convert_encoding('ANTECEDENTES MÉDICOS', 'ISO-8859-1', 'UTF-8'));


$x = $pdf->GetX();
$y = $pdf->GetY();

$pdf->SetFont('Arial', 'B', 10);
$pdf->Cell(0, 6, mb_convert_encoding('Condiciones crónicas:', 'ISO-8859-1', 'UTF-8'), 0, 1);
$pdf->SetFont('Arial', '', 10);

$pdf->Checkbox('Diabetes', $salud['is_diabetes'], $x + 10, $pdf->GetY());
$pdf->Checkbox(mb_convert_encoding('Hipertensión', 'ISO-8859-1', 'UTF-8'), $salud['is_hipertension'], $x + 60, $pdf->GetY());
$pdf->Ln(8);
$pdf->Checkbox(mb_convert_encoding('Cáncer', 'ISO-8859-1', 'UTF-8'), $salud['is_cancer'], $x + 10, $pdf->GetY());
$pdf->Ln(8);


$pdf->SetFont('Arial', 'B', 10);
$pdf->Cell(0, 6, 'Salud reproductiva:', 0, 1);
$pdf->SetFont('Arial', '', 10);

$pdf->Checkbox('Embarazo actual', $salud['is_embarazo'], $x + 10, $pdf->GetY());
$pdf->Ln(8);

if ($salud['is_embarazo']) {
    $pdf->LabelValue('Fecha probable de parto', $salud['fecha_embarazo'], 70);
}

$pdf->Checkbox('Uso de anticonceptivos', $salud['is_anticonceptivo'], $x + 10, $pdf->GetY());
$pdf->Ln(10);


if ($salud['cantidad_embarazo'] > 0) {
    $pdf->SetFont('Arial', 'B', 10);
    $pdf->Cell(0, 6, 'Historial obstétrico:', 0, 1);
    $pdf->SetFont('Arial', '', 10);

    $pdf->LabelValue('Embarazos totales', $salud['cantidad_embarazo'], 70);
    $pdf->LabelValue('Hijos vivos', $salud['cantidad_hijos'], 70);
    $pdf->LabelValue('Abortos', $salud['cantidad_abortos'], 70);
    $pdf->LabelValue('Cesáreas', $salud['cantidad_cesareas'], 70);
    $pdf->Ln(10);
}


$pdf->SectionTitle('HISTORIAL DE CONSULTAS');

if (count($citas) > 0) {
    foreach ($citas as $cita) {
        // Fecha y doctor
        $pdf->SetFont('Arial', 'B', 10);
        $pdf->Cell(0, 6, date('d/m/Y H:i', strtotime($cita['fecha_cita'])) . ' - Dr. ' . mb_convert_encoding($cita['doctor'], 'ISO-8859-1', 'UTF-8'), 0, 1);

        // Detalles
        $pdf->SetFont('Arial', '', 10);
        $pdf->Cell(10);
        $pdf->Cell(0, 6, 'Especialidad: ' . mb_convert_encoding($cita['especialidad'], 'ISO-8859-1', 'UTF-8'), 0, 1);

        $pdf->Cell(10);
        $pdf->Cell(0, 6, 'Consultorio: ' . $cita['consultorio'], 0, 1);

        if (!empty($cita['padecimiento'])) {
            $pdf->Cell(10);
            $pdf->MultiCell(0, 6, 'Motivo/Padecimiento: ' . $cita['padecimiento']);
        }

        $pdf->Ln(5);
    }
} else {
    $pdf->Cell(0, 6, 'No se encontraron consultas registradas', 0, 1);
}


$pdf->Ln(15);
$pdf->Cell(0, 6, '_________________________________________', 0, 1, 'C');
$pdf->Cell(0, 6, mb_convert_encoding('Firma y sello del médico responsable', 'ISO-8859-1', 'UTF-8'), 0, 1, 'C');


$pdf->Output('I', 'Historial_Clinico_' . $paciente['nombrePersona'] . '.pdf');

$conn->close();
