<?php
error_reporting(E_ALL ^ E_WARNING ^ E_NOTICE);
ini_set('display_errors', '1');

$host = 'localhost:3306';
$user = 'root';
$pass = 'Gtagomez1';
$db = 'SLD360';

$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

$idReceta = isset($_GET['id']) ? (int)$_GET['id'] : 0;

if ($idReceta <= 0) {
    die("ID de receta no válido");
}

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


require('../libs/fpdf/fpdf.php');

class PDF extends FPDF
{
    private $title = 'RECETA MÉDICA';

    private $conn;

    public function __construct($conn)
    {
        parent::__construct();

        $this->conn = $conn;
    }

    function Header()
    {

    }

    function Footer()
    {

    }


    function Citas($orientacion)
    {
        $x = 0;
        if ($orientacion == 'izq') {
            $x = 9;
        } else {
            $x = 157;
        }

        $this->SetFont('Arial', '', 12);

        $this->SetXY($x, 53);
        $arrCitas = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameCitasXId',
            'idUsuario' => $_GET['id']
        ]);

        if ($arrCitas->Error) return;

        $arrCitas = $arrCitas->Citas;

        $index = 1;

        foreach ($arrCitas as $cita) {
            $this->Cell(33, 9.5, explode(' ', $cita->fecha)[0], 0, 0, 'C');
            $this->Cell(24, 9.5, explode(' ', $cita->fecha)[1], 0, 0, 'C');
            $this->Cell(43, 9.5, mb_strlen($cita->especialidad, 'UTF-8') > 22 ? mb_convert_encoding(mb_substr($cita->especialidad, 0, 19, 'UTF-8') . '...', 'ISO-8859-1', 'UTF-8') : mb_convert_encoding($cita->especialidad, 'ISO-8859-1', 'UTF-8'), 0, 0);
            $this->Cell(30, 9.5, $index++, 0, 0, 'C');

            $this->SetXY($x, $this->GetY() + 9.5);
        }
    }

    function Generales()
    {
        $arrGenerales = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameGeneralesXId',
            'idUsuario' => $_GET['id']
        ]);

        $this->SetXY(158.5, 15);
        foreach (preg_split('//u', $arrGenerales->curpPersona, -1, PREG_SPLIT_NO_EMPTY) as $letra) {
            $this->Cell(5.7, 8, $letra, 0, 0, 'C');
        }


        if ($arrGenerales->esIndigenaPersona) {
            $this->SetXY(218, 27.5);
            $this->Cell(5.7, 8, 'X', 0, 0, 'C');
        } else {
            $this->SetXY(239, 27.5);
            $this->Cell(5.7, 8, 'X', 0, 0, 'C');
        }

        $this->SetXY(250, 40);
        $this->Cell(5.7, 8, $arrGenerales->tipoSanguineoTxtPersona, 0, 0, 'C');

        $this->SetXY(210, 51);
        $this->Cell(75, 8, $arrGenerales->nombrePersona, 0, 0, 'L');

        $this->SetXY(232, 69);
        $this->Cell(10, 8, $_GET['id'], 0, 0, 'L');

        $this->SetXY(190, 79);
        $this->Cell(30, 8, 'UMF 53', 0, 0, 'L');

        $this->SetXY(195, 87);
        $this->Cell(11, 7, '1', 0, 0, 'C');
        $this->SetXY(214, 87);
        $this->Cell(11, 7, '0', 0, 0, 'C');
        $this->SetXY(233, 87);
        $this->Cell(11, 7, '1', 0, 0, 'C');

        $this->SetXY(240, 103);
        $this->Cell(11, 7, $arrGenerales->sexoPersona, 0, 0, 'C');

        $this->SetXY(190, 115);
        $this->Cell(90, 8, $arrGenerales->calleYNumeroPersona, 0, 0, 'C');

        $this->SetXY(160, 129);
        $this->Cell(50, 8, $arrGenerales->coloniaPersona, 0, 0, 'C');
        $this->Cell(74, 8, $arrGenerales->municipioPersona, 0, 0, 'C');


        $this->SetXY(160, 143);
        $this->Cell(50, 8, $arrGenerales->postalPersona, 0, 0, 'C');
        $this->Cell(74, 8, $arrGenerales->entidadFederativaPersona, 0, 0, 'C');

        $this->SetXY(160, 169);
        $this->Cell(120, 8, $arrGenerales->municipioPersona, 0, 0, 'C');
        $this->SetXY(160, 181);
        $this->Cell(80, 8, $arrGenerales->entidadFederativaPersona, 0, 0, 'C');

        $arrNacimiento = explode('-', $arrGenerales->nacimientoPersona);

        $this->SetXY(238.5, 181);
        $this->Cell(14.5, 8, $arrNacimiento[2], 0, 0, 'C');
        $this->Cell(14.5, 8, $arrNacimiento[1], 0, 0, 'C');
        $this->Cell(14.5, 8, $arrNacimiento[0], 0, 0, 'C');
    }

    function Prenatales()
    {
        $arrCitas = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameCitasXId',
            'idUsuario' => $_GET['id']
        ]);

        if ($arrCitas->Error) return;

        $arrCitas = $arrCitas->Citas;

        $citas = 0;
        foreach ($arrCitas as $arrCita) {
            if (str_contains($arrCita->especialidad, 'Ginecolo')) {
                $citas++;
            }
        }

        if ($citas>9) {$citas=9;}

        if ($citas<1) return;

        $this->SetXY(158, 58);
        $this->Cell(17, 10, '1', 0, 0, 'C');

        for ($i = 1; $i <= $citas; $i++) {
            $this->Cell(6.7, 10, 'X', 0, 0, 'C');
        }

        $this->SetXY(159, 168);
        $this->Cell(27, 10, '1', 0, 0, 'C');

        if ($citas>3) {$citas=3;}

        for ($i = 1; $i <= $citas; $i++) {
            $this->Cell(16.5, 10, 'X', 0, 0, 'C');
        }

        $this->Cell(9.5, 10, '', 0, 0, 'C');
        $this->Cell(9.5, 10, 'X', 0, 0, 'C');
    }

    function VacunasPrimeraEtapa()
    {
        $this->SetXY(103, 46);

        $arrVacunaHepatitis = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 1
        ]);

        $arrVacunaHepatitis = $arrVacunaHepatitis->Vacunas;

        $index = 1;
        foreach ($arrVacunaHepatitis as $vacuna) {
            if ($index == 3) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 10, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 10, $vacuna->lote, 0, 0, 'C');

            $this->SetXY(103, $this->GetY() + 10);

            $index++;
        }


        $this->SetXY(103, 70);

        $arrVacunaTetanos = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 2
        ]);

        $arrVacunaTetanos = $arrVacunaTetanos->Vacunas;

        $index = 1;
        foreach ($arrVacunaTetanos as $vacuna) {
            if ($index == 5) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 10, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 10, $vacuna->lote, 0, 0, 'C');

            $this->SetXY(103, $this->GetY() + 10);

            $index++;
        }

        $this->SetXY(103, 123);

        $arrVacunaTetanos2 = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 3
        ]);

        $arrVacunaTetanos2 = $arrVacunaTetanos2->Vacunas;

        $index = 1;
        foreach ($arrVacunaTetanos2 as $vacuna) {
            if ($index == 2) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 10, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 10, $vacuna->lote, 0, 0, 'C');

            $this->SetXY(103, $this->GetY() + 10);

            $index++;
        }

        $this->SetXY(103, 143);

        $arrVacunaNeumonia = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 4
        ]);

        $arrVacunaNeumonia = $arrVacunaNeumonia->Vacunas;

        $index = 1;
        foreach ($arrVacunaNeumonia as $vacuna) {
            if ($index == 6) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 5, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 5, $vacuna->lote, 0, 0, 'C');

            $this->SetXY(103, $this->GetY() + 5);

            $index++;
        }
    }

    function VacunasSegundaEtapa()
    {
        $this->SetXY(252, 46);

        $arrVacunaHepatitis = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 5
        ]);

        $arrVacunaHepatitis = $arrVacunaHepatitis->Vacunas;

        $index = 1;
        foreach ($arrVacunaHepatitis as $vacuna) {
            if ($index == 4) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 12, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 12, $vacuna->lote, 0, 0, 'C');

            $this->SetXY(252, $this->GetY() + 10);

            $index++;
        }


        $this->SetXY(252, 90);

        $arrVacunaTetanos = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 6
        ]);

        $arrVacunaTetanos = $arrVacunaTetanos->Vacunas;

        $index = 1;
        foreach ($arrVacunaTetanos as $vacuna) {
            if ($index == 2) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 10, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 10, $vacuna->lote, 0, 0, 'C');

            $this->SetXY(252, $this->GetY() + 10);

            $index++;
        }

        $this->SetXY(252, 104);

        $arrVacunaTetanos2 = $this->peticion([
            'servicio' => 'pacientes',
            'funcion' => 'dameVacunasXId',
            'idUsuario' => $_GET['id'],
            'idVacuna' => 7
        ]);

        $arrVacunaTetanos2 = $arrVacunaTetanos2->Vacunas;

        $index = 1;
        foreach ($arrVacunaTetanos2 as $vacuna) {
            if ($index == 7) break;

            $this->SetFont('Arial', '', 9);
            $this->Cell(19, 5, $vacuna->fecha, 0, 0, 'C');

            $this->SetFont('Arial', '', 12);
            $this->Cell(19, 5, $vacuna->lote, 0, 0, 'C');


            if ($index++ > 3) {
                $this->SetXY(252, $this->GetY() + 5.5);
            } else {
                $this->SetXY(252, $this->GetY() + 7.5);
            }
        }
    }

    function peticion($arrDatos)
    {
        $url = "http://localhost/ServicesSLD360/index.php";

        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $arrDatos);

        $response = curl_exec($ch);
        curl_close($ch);

        return json_decode($response);
    }

}

$pdf = new PDF($conn);
$pdf->AliasNbPages();

$pdf->AddPage('L');
$pdf->Image('img/cartilla_001.jpg', 0, 0, 297, 210);


$pdf->AddPage('L');
$pdf->Image('img/cartilla_002.jpg', 0, 0, 297, 210);

$pdf->AddPage('L');
$pdf->Image('img/cartilla_003.jpg', 0, 0, 297, 210);

$pdf->Citas('izq');
$pdf->Generales();

$pdf->AddPage('L');
$pdf->Image('img/cartilla_005.jpg', 0, 0, 297, 210);

$pdf->Citas('izq');

$pdf->AddPage('L');
$pdf->Image('img/cartilla_006.jpg', 0, 0, 297, 210);

$pdf->Citas('der');

$pdf->AddPage('L');
$pdf->Image('img/cartilla_007.jpg', 0, 0, 297, 210);

$pdf->Citas('izq');

$pdf->AddPage('L');
$pdf->Image('img/cartilla_008.jpg', 0, 0, 297, 210);

$pdf->Prenatales();

$pdf->AddPage('L');
$pdf->Image('img/cartilla_010.jpg', 0, 0, 297, 210);
$pdf->VacunasPrimeraEtapa();


$pdf->AddPage('L');
$pdf->Image('img/cartilla_011.jpg', 0, 0, 297, 210);
$pdf->VacunasSegundaEtapa();

$pdf->Output('I', 'Receta_' . $receta['nombre_paciente'] . '.pdf');

$conn->close();
?>