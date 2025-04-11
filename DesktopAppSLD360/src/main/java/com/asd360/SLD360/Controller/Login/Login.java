package com.asd360.SLD360.Controller.Login;

import com.asd360.SLD360.BaseController;
import com.asd360.SLD360.DB.ConexionDB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;

public class Login extends BaseController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Hyperlink registerLink;
    @FXML protected BorderPane rootPane;

    @FXML
    private void handleLoginP(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Por favor complete todos los campos");
            return;
        }

        try (Connection conn = ConexionDB.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call sp_verificar_usuario(?, ?, ?)}");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.execute();

            if (stmt.getInt(3) == 1) {
                BaseController.isLoggedIn = true;
                showSuccessDialog(event);
            } else {
                errorLabel.setText("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            errorLabel.setText("Error de conexión con la base de datos");
            e.printStackTrace();
        }
    }

    private void showSuccessDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inicio de sesión exitoso");
        alert.setHeaderText(null);
        alert.setContentText("Has iniciado sesión correctamente.");

        ButtonType buttonType = new ButtonType("Aceptar");
        alert.getButtonTypes().setAll(buttonType);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonType) {
                try {
                    // Obtén la ventana actual (Stage)
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asd360/SLD360/rool_layout.fxml"));
                    Parent root = loader.load();

                    BaseController controller = loader.getController();
                    controller.loadView("index.fxml");

                    // Crea la nueva escena
                    Scene newScene = new Scene(root);
                    stage.setScene(newScene);

                    // Fuerza el estado maximizado
                    stage.setMaximized(true);

                    // Ejecuta un ajuste final con Platform.runLater
                    Platform.runLater(() -> {
                        stage.setIconified(false); // Asegura que la ventana no esté minimizada
                        stage.setMaximized(true); // Maximiza la ventana nuevamente
                        stage.sizeToScene(); // Ajusta el tamaño a la escena actual
                        stage.centerOnScreen(); // Centra la ventana en la pantalla
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarError("Error al cargar la vista principal");
                }
            }
        });
    }

    @FXML
    private void handleRegisterLink(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asd360/SLD360/Login/registro.fxml"));
            Parent registroView = loader.load();

            // Obtener la escena actual y buscar el BorderPane por su fx:id
            Scene currentScene = ((Node) event.getSource()).getScene();
            BorderPane rootBorderPane = (BorderPane) currentScene.lookup("#rootPane");

            if (rootBorderPane != null) {
                rootBorderPane.setCenter(registroView);
            } else {
                // Alternativa si no se encuentra el BorderPane
                VBox rootVBox = (VBox) currentScene.getRoot();
                // Buscar el BorderPane dentro del VBox
                for (Node node : rootVBox.getChildren()) {
                    if (node instanceof BorderPane && node.getId() != null && node.getId().equals("rootPane")) {
                        ((BorderPane) node).setCenter(registroView);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo cargar el formulario de registro");
        }
    }
}