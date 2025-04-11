package com.asd360.SLD360.Controller.Login;

import com.asd360.SLD360.DB.ConexionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;

public class Registro {
    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    @FXML private Label successLabel;

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = newUsernameField.getText().trim();
        String password = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Todos los campos son obligatorios");
            return;
        }

        if (username.length() < 5) {
            showError("El usuario debe tener al menos 5 caracteres");
            return;
        }

        if (password.length() < 8) {
            showError("La contraseña debe tener al menos 8 caracteres");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Las contraseñas no coinciden");
            return;
        }

        try (Connection conn = ConexionDB.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{call sp_registrar_usuario(?, ?, ?)}");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.execute();

            if (stmt.getInt(3) == 1) {
                showSuccess("¡Registro exitoso! Ahora puede iniciar sesión");
                clearFields();
            } else {
                showError("El nombre de usuario ya existe");
            }
        } catch (Exception e) {
            showError("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asd360/SLD360/Login/login.fxml"));
            Parent loginView = loader.load();

            BorderPane rootBorderPane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
            rootBorderPane.setCenter(loginView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        successLabel.setText("");
    }

    private void showSuccess(String message) {
        successLabel.setText(message);
        errorLabel.setText("");
    }

    private void clearFields() {
        newUsernameField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            // Obtener el BorderPane principal
            BorderPane rootBorderPane = (BorderPane) ((Node) event.getSource()).getScene().getRoot().lookup("#rootPane");

            if (rootBorderPane != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asd360/SLD360/Login/login.fxml"));
                Parent loginView = loader.load();
                rootBorderPane.setCenter(loginView);
            } else {
                mostrarError("No se pudo cargar la vista de login");
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar el formulario de login");
        }
    }

    protected void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al cargar la vista");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}