package com.asd360.SLD360;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class IndexController extends BaseController {

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
}