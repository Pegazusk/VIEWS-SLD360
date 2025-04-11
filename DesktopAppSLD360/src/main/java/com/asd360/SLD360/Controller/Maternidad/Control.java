package com.asd360.SLD360.Maternidad;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Control {

    @FXML
    private HBox ultrasoundContainer; // Contenedor para los ultrasonidos

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private RadioButton routineRadioButton;

    @FXML
    private RadioButton specializedRadioButton;

    private List<Ultrasound> ultrasounds = new ArrayList<>();

    @FXML
    public void initialize() {
        // Inicializar datos de ultrasonidos
        ultrasounds.add(new Ultrasound("12 semanas", LocalDate.of(2024, 3, 15), "/com/asd360/SLD360/img/ultrasound1.jpg", "12 semanas", "Desarrollo normal"));
        ultrasounds.add(new Ultrasound("24 semanas", LocalDate.of(2024, 3, 15), "/com/asd360/SLD360/img/ultrasound2.jpg", "24 semanas", "Posición cefálica"));
        ultrasounds.add(new Ultrasound("32 semanas", LocalDate.of(2024, 3, 15), "/com/asd360/SLD360/img/ultrasound3.jpg", "32 semanas", "Movimientos fetales normales"));

        // Mostrar cada ultrasonido como una tarjeta
        for (Ultrasound ultrasound : ultrasounds) {
            HBox card = createUltrasoundCard(ultrasound);
            ultrasoundContainer.getChildren().add(card);
        }

        // Inicializar ComboBox de horas
        timeComboBox.getItems().addAll("09:00", "10:30", "14:00");
        timeComboBox.setValue("09:00");

        // Inicializar RadioButtons
        ToggleGroup consultTypeGroup = new ToggleGroup();
        routineRadioButton.setToggleGroup(consultTypeGroup);
        specializedRadioButton.setToggleGroup(consultTypeGroup);
        routineRadioButton.setSelected(true);
    }

    private HBox createUltrasoundCard(Ultrasound ultrasound) {
        HBox card = new HBox(10); // Contenedor principal (horizontal)
        card.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10;");

        // Imagen del ultrasonido
        ImageView imageView = new ImageView();
        try {
            // Cargar la imagen usando getClass().getResource()
            Image image = new Image(getClass().getResource(ultrasound.getImage()).toExternalForm());
            imageView.setImage(image);
        } catch (NullPointerException e) {
            // Si la imagen no se encuentra, muestra un mensaje de error
            System.err.println("Error: No se pudo cargar la imagen: " + ultrasound.getImage());
            imageView.setImage(null); // O puedes cargar una imagen de placeholder
        }
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        // Contenedor para el texto (vertical)
        VBox textContainer = new VBox(10);
        textContainer.setStyle("-fx-padding: 10;");

        // Título y descripción
        Label titleLabel = new Label(ultrasound.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #007bff;");

        Label gestationalAgeLabel = new Label("Edad gestacional: " + ultrasound.getGestationalAge());
        gestationalAgeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        gestationalAgeLabel.setWrapText(true); // Ajustar texto automáticamente

        Label observationsLabel = new Label("Observaciones: " + ultrasound.getObservations());
        observationsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        observationsLabel.setWrapText(true); // Ajustar texto automáticamente

        // Agregar elementos al contenedor de texto
        textContainer.getChildren().addAll(titleLabel, gestationalAgeLabel, observationsLabel);

        // Agregar imagen y texto al contenedor principal (HBox)
        card.getChildren().addAll(imageView, textContainer);
        return card;
    }

    @FXML
    private void confirmAppointment() {
        LocalDate date = datePicker.getValue();
        String time = timeComboBox.getValue();
        String consultType = routineRadioButton.isSelected() ? "Control rutinario" : "Estudio especializado";

        if (date != null && time != null) {
            String confirmationDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " a las " + time;
            showFormalConfirmationModal(confirmationDate, consultType);
            clearAppointmentFields(); // Limpiar campos después de agendar
        } else {
            showAlert("Error", "Por favor, complete todos los campos para agendar su cita.");
        }
    }

    private void showFormalConfirmationModal(String confirmationDate, String consultType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Cita");
        alert.setHeaderText("Cita Agendada Exitosamente");
        alert.setContentText(
                "Estimado paciente,\n\n" +
                        "Su cita ha sido programada exitosamente. A continuación, los detalles de su cita:\n\n" +
                        "\u2022 Fecha y Hora: " + confirmationDate + "\n" +
                        "\u2022 Tipo de Consulta: " + consultType + "\n\n" +
                        "Por favor, asegúrese de llegar 15 minutos antes de la hora programada.\n" +
                        "Si tiene alguna duda, no dude en contactarnos.\n\n" +
                        "Atentamente,\nCentro Médico SLD360"
        );
        alert.showAndWait();
    }

    private void clearAppointmentFields() {
        datePicker.setValue(null);
        timeComboBox.setValue("09:00");
        routineRadioButton.setSelected(true);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class Ultrasound {
        private String title;
        private LocalDate date;
        private String image;
        private String gestationalAge;
        private String observations;

        public Ultrasound(String title, LocalDate date, String image, String gestationalAge, String observations) {
            this.title = title;
            this.date = date;
            this.image = image;
            this.gestationalAge = gestationalAge;
            this.observations = observations;
        }

        public String getTitle() {
            return title;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getImage() {
            return image;
        }

        public String getGestationalAge() {
            return gestationalAge;
        }

        public String getObservations() {
            return observations;
        }
    }
}