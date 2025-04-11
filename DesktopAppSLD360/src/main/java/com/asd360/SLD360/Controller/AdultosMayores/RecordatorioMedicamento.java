package com.asd360.SLD360.AdultosMayores;

import com.asd360.SLD360.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RecordatorioMedicamento extends BaseController {

    @FXML
    private TextField medicationName;

    @FXML
    private TextField reminderDate;

    @FXML
    private TextField reminderTime;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleSetReminder() {
        String name = medicationName.getText().trim();
        String date = reminderDate.getText().trim();
        String time = reminderTime.getText().trim();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            showMessage("Por favor, complete todos los campos.", "red");
            return;
        }

        if (!isValidDate(date)) {
            showMessage("Formato de fecha inválido. Use dd/MM/yyyy.", "red");
            return;
        }

        if (!isValidTime(time)) {
            showMessage("Formato de hora inválido. Use HH:mm.", "red");
            return;
        }

        // Mostrar notificación de confirmación
        showConfirmationAlert(name, date, time);

        medicationName.clear();
        reminderDate.clear();
        reminderTime.clear();
    }

    private boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void showMessage(String message, String color) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private void showConfirmationAlert(String name, String date, String time) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recordatorio Establecido");
        alert.setHeaderText(null);
        alert.setContentText("¡Recordatorio para " + name + " el " + date + " a las " + time + " establecido con éxito!");
        alert.showAndWait();
    }
}
