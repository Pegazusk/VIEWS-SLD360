package com.asd360.SLD360.Maternidad;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutricionMaternalController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField telefonoField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextField horaField;

    @FXML
    private Label mensajeLabel;

    // Método para manejar el evento de agendar cita
    @FXML
    private void agendarCita(ActionEvent event) {
        String nombre = nombreField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String fecha = (fechaPicker.getValue() != null) ? fechaPicker.getValue().toString() : "";
        String hora = horaField.getText().trim();

        // Validación de campos
        if (nombre.isEmpty() || telefono.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            mensajeLabel.setText("Todos los campos son obligatorios.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validación del número de teléfono
        if (!telefono.matches("\\d{10,15}")) {
            mensajeLabel.setText("Ingrese un número de WhatsApp válido.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Crear un objeto con los datos de la cita
        // Se eliminó la clase Nutricion_Maternal y ahora los datos se envían directamente
        String citaJson = crearCitaJson(nombre, telefono, fecha, hora);

        // Enviar la cita al servidor REST
        enviarCitaAlServidor(citaJson);

        // Mostrar mensaje de confirmación
        mensajeLabel.setText("Cita agendada. Se ha enviado un mensaje a WhatsApp.");
        mensajeLabel.setStyle("-fx-text-fill: green;");

        // Limpiar campos
        nombreField.clear();
        telefonoField.clear();
        fechaPicker.setValue(null);
        horaField.clear();
    }

    // Método para crear el JSON de la cita
    private String crearCitaJson(String nombre, String telefono, String fecha, String hora) {
        // Crear un objeto que contenga los datos de la cita y convertirlo a JSON
        String json = "{"
                + "\"nombrePaciente\": \"" + nombre + "\","
                + "\"numeroPaciente\": \"" + telefono + "\","
                + "\"fecha\": \"" + fecha + "\","
                + "\"hora\": \"" + hora + "\""
                + "}";

        return json;
    }

    // Método para enviar la cita al servidor REST
    private void enviarCitaAlServidor(String citaJson) {
        try {
            // URL del servidor REST
            URL url = new URL("http://tuservidor.com/api/Maternidad/agendarCita");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Enviar los datos
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = citaJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Verificar la respuesta del servidor
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error al enviar la cita: Código " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al enviar la cita al servidor.");
            mensajeLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
