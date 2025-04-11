package com.asd360.SLD360.ComunidadRural;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.event.ActionEvent;
import java.awt.Desktop;
import java.net.URI;

public class MordeduraSerpienteController {

    @FXML
    private WebView webView;

    @FXML
    private void handleEmergencyCall(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("tel:911"));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("No se pudo realizar la llamada de emergencia.");
        }
    }

    @FXML
    public void initialize() {
        webView.getEngine().load("https://www.youtube.com/embed/OX4GkeJruCU");

        webView.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                webView.prefWidthProperty().bind(newScene.widthProperty().subtract(40));
                webView.prefHeightProperty().bind(newScene.heightProperty().divide(2));
            }
        });
    }

    private void mostrarError(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}