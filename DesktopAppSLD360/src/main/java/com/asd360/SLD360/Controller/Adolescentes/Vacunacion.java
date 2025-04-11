package com.asd360.SLD360.Adolescentes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Vacunacion {

    @FXML
    private TextField edadField;

    @FXML
    private VBox vacunasList;

    @FXML
    private WebView mapWebView;

    @FXML
    private StackPane vacunaModal;

    @FXML
    private Text vacunaTitle;

    @FXML
    private Text vacunaDesc;

    @FXML
    private ListView<String> vacunaPreviene;

    @FXML
    private ListView<String> vacunaRiesgos;

    private List<Vacuna> vacunas = List.of(
            new Vacuna("BCG (Tuberculosis)", 0, "Vacuna contra la tuberculosis",
                    List.of("Tuberculosis miliar", "Meningitis tuberculosa"),
                    List.of("Inflamación local", "Úlcera pequeña")),

            new Vacuna("Hepatitis B (primera dosis)", 0, "Protección contra la hepatitis B",
                    List.of("Hepatitis B", "Cirrosis hepática"),
                    List.of("Dolor en el lugar de inyección", "Fiebre baja")),

            new Vacuna("Pentavalente (DTP + Hib + Hepatitis B)", 2, "Combina protección contra 5 enfermedades",
                    List.of("Difteria", "Tétanos", "Tos ferina", "Hepatitis B", "Influenza tipo B"),
                    List.of("Fiebre moderada", "Irritabilidad")),

            new Vacuna("Polio (OPV - oral)", 2, "Vacuna oral contra la poliomielitis",
                    List.of("Poliomielitis"),
                    List.of("Ninguno común")),

            new Vacuna("Rotavirus", 2, "Protección contra el rotavirus",
                    List.of("Gastroenteritis por rotavirus"),
                    List.of("Irritabilidad", "Diarrea leve")),

            new Vacuna("Neumococo conjugada", 2, "Protección contra enfermedades neumocócicas",
                    List.of("Neumonía", "Meningitis", "Otitis media"),
                    List.of("Fiebre", "Hinchazón en el lugar de la inyección")),

            new Vacuna("Pentavalente (segunda dosis)", 4, "Refuerzo para difteria, tétanos, tos ferina, hepatitis B y Haemophilus influenzae tipo b",
                    List.of("Difteria", "Tétanos", "Tos ferina", "Hepatitis B", "Influenza tipo B"),
                    List.of("Fiebre moderada", "Irritabilidad")),

            new Vacuna("Polio (OPV - segunda dosis)", 4, "Refuerzo para poliomielitis",
                    List.of("Poliomielitis"),
                    List.of("Ninguno común")),

            new Vacuna("Rotavirus (segunda dosis)", 4, "Refuerzo para rotavirus",
                    List.of("Gastroenteritis por rotavirus"),
                    List.of("Irritabilidad", "Diarrea leve")),

            new Vacuna("Neumococo conjugada (segunda dosis)", 4, "Refuerzo para enfermedades neumocócicas",
                    List.of("Neumonía", "Meningitis", "Otitis media"),
                    List.of("Fiebre", "Hinchazón en el lugar de la inyección")),

            new Vacuna("Influenza (gripe)", 6, "Protección contra la influenza estacional",
                    List.of("Influenza"),
                    List.of("Dolor en el lugar de inyección", "Fiebre baja")),

            new Vacuna("Pentavalente (tercera dosis)", 6, "Refuerzo para difteria, tétanos, tos ferina, hepatitis B y Haemophilus influenzae tipo b",
                    List.of("Difteria", "Tétanos", "Tos ferina", "Hepatitis B", "Influenza tipo B"),
                    List.of("Fiebre moderada", "Irritabilidad")),

            new Vacuna("Polio (OPV - tercera dosis)", 6, "Refuerzo para poliomielitis",
                    List.of("Poliomielitis"),
                    List.of("Ninguno común")),

            new Vacuna("Triple viral (SRP - Sarampión, Rubéola, Paperas)", 12, "Protección contra sarampión, rubéola y paperas",
                    List.of("Sarampión", "Rubéola", "Paperas"),
                    List.of("Fiebre", "Erupción cutánea leve")),

            new Vacuna("Varicela", 12, "Protección contra la varicela",
                    List.of("Varicela"),
                    List.of("Fiebre", "Erupción cutánea leve")),

            new Vacuna("Hepatitis A", 12, "Protección contra la hepatitis A",
                    List.of("Hepatitis A"),
                    List.of("Dolor en el lugar de inyección", "Fiebre baja")),

            new Vacuna("DTP (Refuerzo)", 18, "Refuerzo para difteria, tétanos y tos ferina",
                    List.of("Difteria", "Tétanos", "Tos ferina"),
                    List.of("Fiebre", "Hinchazón en el lugar de la inyección")),

            new Vacuna("Polio (IPV - inyectable)", 18, "Vacuna inactivada contra la poliomielitis",
                    List.of("Poliomielitis"),
                    List.of("Dolor en el lugar de inyección")),

            new Vacuna("MMR (Refuerzo - Sarampión, Rubéola, Paperas)", 48, "Refuerzo para sarampión, rubéola y paperas",
                    List.of("Sarampión", "Rubéola", "Paperas"),
                    List.of("Fiebre", "Erupción cutánea leve")),

            new Vacuna("DTPa (Refuerzo)", 48, "Refuerzo para difteria, tétanos y tos ferina",
                    List.of("Difteria", "Tétanos", "Tos ferina"),
                    List.of("Fiebre", "Hinchazón en el lugar de la inyección")),

            new Vacuna("Tétanos (Refuerzo)", 72, "Refuerzo para tétanos",
                    List.of("Tétanos"),
                    List.of("Dolor en el lugar de inyección", "Fiebre baja")),

            new Vacuna("Sarampión (Refuerzo)", 72, "Refuerzo para sarampión",
                    List.of("Sarampión"),
                    List.of("Fiebre", "Erupción cutánea leve")),

            new Vacuna("HPV", 120, "Protección contra el virus del papiloma humano",
                    List.of("Cáncer cervical", "Verrugas genitales"),
                    List.of("Dolor en el lugar de inyección", "Mareos")),

            new Vacuna("Meningococo", 120, "Protección contra la meningitis meningocócica",
                    List.of("Meningitis", "Septicemia"),
                    List.of("Fiebre", "Dolor en el lugar de inyección"))
    );

    private Stage primaryStage; // Declarar primaryStage

    // Establecer el Stage en el controlador
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void calcularVacunas() {
        int edad = Integer.parseInt(edadField.getText());
        vacunasList.getChildren().clear();

        List<Vacuna> vacunasOrdenadas = vacunas.stream()
                .filter(vacuna -> vacuna.getEdad() <= edad)
                .sorted((v1, v2) -> Integer.compare(v1.getEdad(), v2.getEdad()))
                .collect(Collectors.toList());

        for (Vacuna vacuna : vacunasOrdenadas) {
            VBox vacunaInfo = new VBox();
            vacunaInfo.setSpacing(5);

            Text nombreVacuna = new Text(vacuna.getNombre());
            nombreVacuna.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Text edadVacuna = new Text("Edad recomendada: " + vacuna.getEdad() + " meses");
            edadVacuna.setStyle("-fx-font-size: 12px;");

            Text descVacuna = new Text("Para qué sirve: " + vacuna.getDesc());
            descVacuna.setStyle("-fx-font-size: 12px;");

            vacunaInfo.getChildren().addAll(nombreVacuna, edadVacuna, descVacuna);

            Button vacunaButton = new Button();
            vacunaButton.setGraphic(vacunaInfo);
            vacunaButton.setMaxWidth(Double.MAX_VALUE);

            vacunasList.getChildren().add(vacunaButton);
        }
    }


    @FXML
    private void cerrarModal() {
        vacunaModal.setVisible(false);
    }


    @FXML
    private void irCentroVacunacion() {
        WebEngine webEngine = mapWebView.getEngine();
        webEngine.load("https://www.google.com/maps");
    }

    public static class Vacuna {
        private final String nombre;
        private final int edad;
        private final String desc;
        private final List<String> previene;
        private final List<String> riesgos;

        public Vacuna(String nombre, int edad, String desc, List<String> previene, List<String> riesgos) {
            this.nombre = nombre;
            this.edad = edad;
            this.desc = desc;
            this.previene = previene;
            this.riesgos = riesgos;
        }

        public String getNombre() {
            return nombre;
        }

        public int getEdad() {
            return edad;
        }

        public String getDesc() {
            return desc;
        }

        public List<String> getPreviene() {
            return previene;
        }

        public List<String> getRiesgos() {
            return riesgos;
        }
    }
}