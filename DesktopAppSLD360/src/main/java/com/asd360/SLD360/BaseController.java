package com.asd360.SLD360;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    protected static boolean isLoggedIn = false;

    @FXML
    private MenuItem loginMenuItem;

    // Método para actualizar el texto del menú según el estado de sesión
    public void updateAuthMenu() {
        if (loginMenuItem != null) {
            loginMenuItem.setText(isLoggedIn ? "Cerrar Sesión" : "Iniciar Sesión");
        }
    }

    // Método que maneja tanto login como logout
    @FXML
    private void handleAuthAction(ActionEvent event) {
        if (isLoggedIn) {
            // Mostrar confirmación de cerrar sesión
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar Sesión");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                isLoggedIn = false;
                updateAuthMenu();
                handleGoToIndex(); // Volver al inicio
            }
        } else {
            loadView("Login/login.fxml"); // Redirigir al login
        }
    }

    @FXML
    protected BorderPane rootPane;

    public void loadView(String fxmlFile) {
        if (rootPane == null) {
            System.out.println("rootPane aún no está inicializado");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asd360/SLD360/" + fxmlFile));
            Parent newView = loader.load();

            if (newView instanceof BorderPane) {
                BorderPane borderPane = (BorderPane) newView;
                rootPane.setCenter(borderPane.getCenter());
            } else {
                rootPane.setCenter(newView);
            }

            updateAuthMenu(); // Actualizar el menú después de cargar la vista
        } catch (IOException e) {
            mostrarError("No se pudo cargar la vista: " + fxmlFile);
            e.printStackTrace();
        }
    }

    protected void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al cargar la vista");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    protected void handleGoToIndex() {
        loadView("index.fxml");
    }

    @FXML
    private void handleEmergencyCall(ActionEvent event) {
        System.out.println("Llamada a Emergencias");
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("tel:911"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar realizar la llamada.");
        }
    }

    @FXML
    private void handleControl(ActionEvent event) {
        loadView("Maternidad/control.fxml");
    }

    @FXML
    private void handleNutricion(ActionEvent event) {
        loadView("Maternidad/nutricion_maternal.fxml");
    }

    @FXML
    private void handleContracciones(ActionEvent event) {
        loadView("Maternidad/contracciones.fxml");
    }

    @FXML
    private void handleVacunacion(ActionEvent event) {
        loadView("Adolescentes/vacunacion.fxml");
    }

    @FXML
    private void handleFiebre(ActionEvent event) {
        loadView("Adolescentes/fiebre.fxml");
    }

    @FXML
    private void handleBullying(ActionEvent event) {
        loadView("Adolescentes/bullying.fxml");
    }

    @FXML
    private void handleDolorPecho(ActionEvent event) {loadView("AdultosMayores/dolor_pecho.fxml");}

    @FXML
    private void handleCaidas(ActionEvent event) {
        loadView("AdultosMayores/caidas.fxml");
    }

    @FXML
    private void handleMedicamento(ActionEvent event) {
        loadView("AdultosMayores/recordatorio_medicamento.fxml");
    }

    @FXML
    private void handleLengua(ActionEvent event) {
        loadView("Discapacidad/lengua.fxml");
    }

    @FXML
    private void handleAnsiedad(ActionEvent event) {
        loadView("Discapacidad/crisis_ansiedad.fxml");
    }

    @FXML
    private void handleAtencionMovil(ActionEvent event) {
        loadView("ComunidadRural/atencion_movil.fxml");
    }

    @FXML
    private void handleMordedura(ActionEvent event) {
        loadView("ComunidadRural/mordedura_serpiente.fxml");
    }

    @FXML
    private void handleEducacion(ActionEvent event) {loadView("ComunidadRural/educacion_sanitaria.fxml");}

    @FXML
    private void handleSalud(ActionEvent event) {
        loadView("VidaSexual/salud_sexual.fxml");
    }

    @FXML
    private void handleApoyoPsicologico(ActionEvent event) {
        loadView("VidaSexual/apoyo_psicologico.fxml");
    }

    @FXML
    private void handlePrevencion(ActionEvent event) {
        loadView("VidaSexual/prevencion.fxml");
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        loadView("Login/login.fxml");  // Cargar el formulario de login en el BorderPane actual
    }

}