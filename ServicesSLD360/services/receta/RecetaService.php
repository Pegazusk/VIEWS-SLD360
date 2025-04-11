<?php
class RecetaService extends Service
{
    public function guardarReceta()
    {
        $idPaciente = $_POST['idPaciente'];
        $idDoctor = $this->dameIdDoctor($_POST['idDoctor']);
        $fecha = $_POST['fecha'];
        $diagnostico = $_POST['diagnostico'];
        $indicaciones = $_POST['indicaciones'];
        $medicamentos = json_decode($_POST['medicamentos'], true);
        
        // Insertar receta principal
        $this->conexion->consulta(
            "INSERT INTO receta (fk_paciente, fk_doctor, fecha, diagnostico, indicaciones) 
             VALUES ($idPaciente, $idDoctor, '$fecha', '$diagnostico', '$indicaciones')"
        );
        
        $idReceta = $this->conexion->obtenerUltimoId();
        
        // Insertar medicamentos
        foreach ($medicamentos as $med) {
            $this->conexion->consulta(
                "INSERT INTO receta_medicamento 
                 (fk_receta, medicamento, presentacion, dosis, frecuencia, duracion) 
                 VALUES (
                    $idReceta, 
                    '{$med['nombre']}', 
                    '{$med['presentacion']}', 
                    '{$med['dosis']}', 
                    '{$med['frecuencia']}', 
                    '{$med['duracion']}'
                 )"
            );
        }
        
        return ['Error' => false, 'ErrorMensaje' => '', 'idReceta' => $idReceta];
    }
    
    private function dameIdDoctor($idUsuario)
    {
        $result = $this->conexion->consulta(
            "SELECT id FROM doctor WHERE fk_persona = (
                SELECT id FROM persona WHERE fk_usuario = $idUsuario
            )"
        );
        $row = $this->conexion->fila($result);
        return $row['id'];
    }

    public function obtenerRecetasPorPaciente()
    {
        try {
            // Validar parámetros
            if (!isset($_POST['idPaciente']) || !isset($_POST['idDoctor'])) {
                throw new Exception("Parámetros incompletos");
            }

            $idPaciente = (int)$_POST['idPaciente'];
            $idDoctor = $this->dameIdDoctor($_POST['idDoctor']);

            // Validar IDs
            if ($idPaciente <= 0) {
                throw new Exception("ID de paciente inválido");
            }

            if ($idDoctor <= 0) {
                throw new Exception("No se pudo obtener el ID del doctor");
            }

            // Consulta para obtener recetas
            $query = "SELECT id, fecha, diagnostico, indicaciones 
                      FROM receta 
                      WHERE fk_paciente = $idPaciente AND fk_doctor = $idDoctor
                      ORDER BY fecha DESC";
            
            $result = $this->conexion->consulta($query);
            
            if (!$result) {
                throw new Exception("Error en consulta: " . $this->conexion->error());
            }

            $recetas = [];
            while ($row = $this->conexion->fila($result)) {
                // Obtener medicamentos para cada receta
                $medicamentos = $this->obtenerMedicamentosReceta($row['id']);
                $row['medicamentos'] = $medicamentos;
                $recetas[] = $row;
            }

            return [
                'Error' => false,
                'ErrorMensaje' => '',
                'Recetas' => $recetas
            ];

        } catch (Exception $e) {
            return [
                'Error' => true,
                'ErrorMensaje' => $e->getMessage(),
                'Recetas' => []
            ];
        }
    }

    private function obtenerMedicamentosReceta($idReceta)
    {
        $query = "SELECT medicamento, presentacion, dosis, frecuencia, duracion
                  FROM receta_medicamento
                  WHERE fk_receta = $idReceta";
        
        $result = $this->conexion->consulta($query);
        
        if (!$result) {
            throw new Exception("Error al obtener medicamentos: " . $this->conexion->error());
        }

        $medicamentos = [];
        while ($row = $this->conexion->fila($result)) {
            $medicamentos[] = $row;
        }

        return $medicamentos;
    }
    
}
?>