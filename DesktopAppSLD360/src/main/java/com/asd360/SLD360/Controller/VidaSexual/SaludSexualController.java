package com.asd360.SLD360.VidaSexual;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class SaludSexualController {

    @FXML
    private VBox etsContainer;

    public static class ETS {
        String nombre;
        String sintomas;
        String prevencion;
        String tratamiento;

        public ETS(String nombre, String sintomas, String prevencion, String tratamiento) {
            this.nombre = nombre;
            this.sintomas = sintomas;
            this.prevencion = prevencion;
            this.tratamiento = tratamiento;
        }
    }

    private final ETS[] etsData = new ETS[] {
            new ETS("VIH/SIDA", "Fiebre, fatiga, dolor de garganta, pérdida de peso.",
                    "Uso de preservativos, no compartir agujas, PrEP (profilaxis preexposición).",
                    "Antirretrovirales (ART)."),
            new ETS("Sífilis", "Llagas indoloras, erupciones cutáneas, fiebre, dolor de cabeza.",
                    "Uso de preservativos, pruebas regulares.",
                    "Penicilina."),
            new ETS("Gonorrea", "Dolor al orinar, secreción anormal, dolor pélvico.",
                    "Uso de preservativos, pruebas regulares.",
                    "Antibióticos (ceftriaxona y azitromicina)."),
            new ETS("Clamidia", "Secreción anormal, dolor al orinar, dolor durante las relaciones sexuales.",
                    "Uso de preservativos, pruebas regulares.",
                    "Antibióticos (azitromicina o doxiciclina)."),
            new ETS("Herpes Genital", "Ampollas dolorosas, picazón, úlceras.",
                    "Uso de preservativos, evitar contacto durante brotes.",
                    "Antivirales (aciclovir, valaciclovir)."),
            new ETS("VPH (Virus del Papiloma Humano)", "Verrugas genitales, cambios en el cuello uterino (en mujeres).",
                    "Vacunación, uso de preservativos.",
                    "Tratamiento de verrugas, seguimiento médico.")
    };

    @FXML
    public void initialize() {
        for (ETS ets : etsData) {
            Label nombreLabel = new Label(ets.nombre);
            nombreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-alignment: center;");

            Text sintomasText = new Text("Síntomas: " + ets.sintomas);
            Text prevencionText = new Text("Prevención: " + ets.prevencion);
            Text tratamientoText = new Text("Tratamiento: " + ets.tratamiento);

            VBox etsCard = new VBox(5, nombreLabel, sintomasText, prevencionText, tratamientoText);
            etsCard.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-width: 1px;");
            etsCard.setAlignment(javafx.geometry.Pos.CENTER);

            // Añadir la tarjeta a la lista de enfermedades
            etsContainer.getChildren().add(etsCard);
        }
    }
}
