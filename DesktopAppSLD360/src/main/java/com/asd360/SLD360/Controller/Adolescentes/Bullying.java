package com.asd360.SLD360.Adolescentes;

import com.asd360.SLD360.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Bullying extends BaseController {

    @FXML private TextField reporterName;
    @FXML private TextField incidentDetails;
    @FXML private Label statusMessage;

    @FXML private TextField chatbotInput;
    @FXML private VBox chatMessages;

    @FXML
    private void handleReportSubmit(ActionEvent event) {
        String name = reporterName.getText();
        String details = incidentDetails.getText();

        if (name.isEmpty() || details.isEmpty()) {
            showAlert("Error", "Por favor, completa todos los campos.");
        } else {
            statusMessage.setText("¡Gracias por tu denuncia! Nuestro equipo se pondrá en contacto.");
        }
    }

    @FXML
    private void handleChatbotMessage(ActionEvent event) {
        String userMessage = chatbotInput.getText();

        if (!userMessage.isEmpty()) {
            TextFlow userTextFlow = new TextFlow(new Text("Tú: " + userMessage));
            userTextFlow.setStyle("-fx-background-color: #e9ecef; -fx-padding: 5px; -fx-background-radius: 5px;");
            chatMessages.getChildren().add(userTextFlow);

            TextFlow botTextFlow = new TextFlow(new Text("Asistente: Gracias por tu mensaje. Un consejero se pondrá en contacto contigo pronto. Mientras tanto, te recomiendo:\n" +
                    "- Hablar con un adulto de confianza\n" +
                    "- Guardar evidencia del acoso\n" +
                    "- Llamar al 911 si estás en peligro"));
            botTextFlow.setStyle("-fx-background-color: #d1ecf1; -fx-padding: 5px; -fx-background-radius: 5px;");

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.runLater(() -> {
                    chatMessages.getChildren().add(botTextFlow);
                    chatMessages.setSpacing(10);
                });
            }).start();

            chatbotInput.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}