package com.asd360.SLD360.VidaSexual;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class ApoyoPsicologicoController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField correoField;

    @FXML
    private TextField telefonoField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextField horaField;

    @FXML
    private Label mensajeLabel;

    @FXML
    private void agendarCita(ActionEvent event) {
        String nombre = nombreField.getText().trim();
        String correo = correoField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String fecha = (fechaPicker.getValue() != null) ? fechaPicker.getValue().toString() : "";
        String hora = horaField.getText().trim();

        // Validar si hay campos vacíos
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            mensajeLabel.setText("Todos los campos son obligatorios.");
            mensajeLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        } else {
            // Mostrar mensaje de confirmación con los detalles
            String mensajeExito = String.format("Cita agendada con éxito para %s.\nCorreo: %s\nTeléfono: %s\nFecha: %s\nHora: %s",
                    nombre, correo, telefono, fecha, hora);
            mensajeLabel.setText(mensajeExito);
            mensajeLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px; -fx-font-weight: bold;");

            // Limpiar los campos después de agendar
            nombreField.clear();
            correoField.clear();
            telefonoField.clear();
            fechaPicker.setValue(null);
            horaField.clear();
        }
    }
}
