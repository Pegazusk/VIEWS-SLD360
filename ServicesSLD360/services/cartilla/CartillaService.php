<?php
class CartillaService extends Service {
    
    public function generarCartillaPDF() {
        $idPaciente = $_POST['idPaciente'];
        $idDoctor = $this->dameIdDoctor($_POST['idDoctor']);
        
        // Obtener datos del paciente
        $paciente = $this->conexion->consulta(
            "SELECT p.*, TIMESTAMPDIFF(YEAR, p.fecha_nacimiento, CURDATE()) as edad
             FROM paciente pa
             JOIN persona p ON pa.fk_persona = p.id
             WHERE pa.id = $idPaciente"
        )->fila();
        
        // Obtener cartilla
        $cartilla = $this->conexion->consulta(
            "SELECT * FROM cartilla_vacunacion 
             WHERE id_paciente = $idPaciente
             ORDER BY fecha_creacion DESC LIMIT 1"
        )->fila();
        
        // Obtener vacunas
        $vacunas = $this->conexion->consulta(
            "SELECT * FROM vacunas_aplicadas
             WHERE id_cartilla = {$cartilla['id']}
             ORDER BY fecha_aplicacion"
        )->fetchAll();
        
        // Obtener antecedentes
        $antecedentes = $this->conexion->consulta(
            "SELECT * FROM antecedentes_paciente
             WHERE id_cartilla = {$cartilla['id']}
             ORDER BY tipo, fecha_registro"
        )->fetchAll();
        
        // Generar PDF
        require_once('../libs/fpdf/fpdf.php');
        
        $pdf = new CartillaPDF();
        $pdf->generarCartilla($paciente, $cartilla, $vacunas, $antecedentes);
        
        $nombreArchivo = 'Cartilla_Vacunacion_'.$paciente['nombre'].'_'.$paciente['apellido_uno'].'.pdf';
        $pdf->Output('I', $nombreArchivo);
        
        return ['Error' => false];
    }
    
    public function agregarVacuna() {
        $datos = $_POST;
        $this->conexion->consulta(
            "INSERT INTO vacunas_aplicadas 
             (id_cartilla, nombre_vacuna, fecha_aplicacion, dosis, lote, aplicador, edad_aplicacion)
             VALUES (
                {$datos['idCartilla']},
                '{$datos['nombre_vacuna']}',
                '{$datos['fecha_aplicacion']}',
                {$datos['dosis']},
                '{$datos['lote']}',
                '{$datos['aplicador']}',
                '{$datos['edad_aplicacion']}'
             )"
        );
        return ['Error' => false, 'idVacuna' => $this->conexion->obtenerUltimoId()];
    }
}