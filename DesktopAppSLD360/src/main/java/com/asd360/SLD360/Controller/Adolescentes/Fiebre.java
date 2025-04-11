package com.asd360.SLD360.Adolescentes;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Fiebre {

    @FXML
    private TextField temperatureInput;

    @FXML
    private CheckBox unitSwitch;

    @FXML
    private Label resultAlert;

    @FXML
    private Rectangle thermometerFill;  // Este es el que se llena según la temperatura

    @FXML
    private Rectangle thermometerBackground;  // El marco exterior del termómetro

    public void calculateFever() {
        try {
            double temp = Double.parseDouble(temperatureInput.getText());
            if (Double.isNaN(temp)) return;

            if (unitSwitch.isSelected()) {
                temp = (temp * 9 / 5) + 32; // Conversión a Fahrenheit si es necesario
            }

            String status, color, message;
            double thermometerHeight = 0; // Altura del termómetro (nivel de llenado)

            // Evaluar el rango de la temperatura y asignar color y altura
            if ((!unitSwitch.isSelected() && temp < 34) || (unitSwitch.isSelected() && temp < 93.2)) {
                status = "Hipotermia";
                color = "blue";
                message = "Busca atención médica inmediata. Mantén al niño abrigado y evita la exposición al frío.";
                thermometerHeight = 40; // Bajo
            } else if ((!unitSwitch.isSelected() && temp < 37.6) || (unitSwitch.isSelected() && temp < 99.7)) {
                status = "Normal";
                color = "green";
                message = "Temperatura dentro de rangos normales.";
                thermometerHeight = 100; // Normal
            } else if ((!unitSwitch.isSelected() && temp < 38.5) || (unitSwitch.isSelected() && temp < 101.3)) {
                status = "Febrícula";
                color = "orange";
                message = "Monitorizar temperatura y mantener hidratación.";
                thermometerHeight = 150; // Leveemente elevada
            } else {
                status = "Fiebre Alta";
                color = "red";
                message = "Administrar antipirético y consultar médico.";
                thermometerHeight = 200; // Fiebre alta
            }

            if ((!unitSwitch.isSelected() && temp >= 40) || (unitSwitch.isSelected() && temp >= 104)) {
                message += "\n\nConsejo: La temperatura es extremadamente alta. Busca atención médica urgente y utiliza métodos de enfriamiento (paños húmedos, baño tibio).";
            } else if ((!unitSwitch.isSelected() && temp <= 35) || (unitSwitch.isSelected() && temp <= 95)) {
                message += "\n\nConsejo: La temperatura es extremadamente baja. Busca atención médica urgente y abriga al niño con mantas térmicas.";
            } else if ((!unitSwitch.isSelected() && temp >= 36) && (unitSwitch.isSelected() && temp >= 96.8) && temp <= 37.6) {
                message += "\n\nConsejo: La temperatura está dentro de los rangos normales. Sigue monitoreando la salud del niño.";
            }

            resultAlert.setStyle("-fx-text-fill: " + color + ";");
            resultAlert.setText(String.format("%s (%.1f%s)\n%s", status, temp, unitSwitch.isSelected() ? "°F" : "°C", message));

            // Cambiar el tamaño de la fuente del Label con los consejos
            resultAlert.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 16px;");

            // Ajustamos la altura del termómetro interior (thermometerFill)
            thermometerFill.setHeight(thermometerHeight); // Se ajusta directamente el termómetro existente
            thermometerFill.setFill(Color.web(color));  // Cambiar color según la temperatura

        } catch (NumberFormatException e) {
            resultAlert.setText("Por favor ingresa una temperatura válida.");
        }
    }

}
