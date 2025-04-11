package com.asd360.SLD360.Controller.Discapacidad;

import com.asd360.SLD360.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class Lengua extends BaseController {

    @FXML
    private TextField userPhoneField;

    @FXML
    private void handleSendMessage() {
        String userPhone = userPhoneField.getText().trim();

        if (!userPhone.matches("^\\+[0-9]{10,}$")) {
            mostrarError("Número inválido. Debe incluir código de país (ej: +5218116542698).");
            return;
        }

        String doctorNumber = "+5218116542698";

        try {
            String message = "Hola, " + "Necesito ayuda con lengua de señas.";

            String encodedMessage = URLEncoder.encode(message, "UTF-8");

            String whatsappUrl = "https://wa.me/" + doctorNumber.replaceAll("[^+0-9]", "") +
                    "?text=" + encodedMessage;

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(whatsappUrl));
            } else {
                mostrarError("No se pudo abrir WhatsApp. Ábrelo manualmente.");
            }
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }
}