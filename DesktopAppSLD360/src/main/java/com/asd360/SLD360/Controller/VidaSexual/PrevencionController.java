package com.asd360.SLD360.VidaSexual;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;

public class PrevencionController {

    @FXML
    private WebView mapView;

    @FXML
    private Button obtenerUbicacionButton;

    @FXML
    public void initialize() {
        WebEngine webEngine = mapView.getEngine();
        webEngine.load("https://www.google.com/maps");

        // Habilitar geolocalización en el WebView
        webEngine.setUserDataDirectory(java.nio.file.Paths.get(System.getProperty("user.home"), ".javafx").toFile());

        // Asignar la acción del botón
        obtenerUbicacionButton.setOnAction(event -> obtenerUbicacion());
    }

    private void obtenerUbicacion() {
        WebEngine webEngine = mapView.getEngine();

        // Verificar si se soporta geolocalización en el WebView
        webEngine.executeScript(
                "if (navigator.geolocation) { " +
                        "  navigator.geolocation.getCurrentPosition(function(position) { " +
                        "    var latitude = position.coords.latitude; " +
                        "    var longitude = position.coords.longitude; " +
                        "    window.location.href = 'https://www.google.com/maps?q=' + latitude + ',' + longitude; " +
                        "  }, function() { " +
                        "    alert('No se pudo obtener la ubicación.');" +
                        "  }); " +
                        "} else { " +
                        "  alert('La geolocalización no es compatible con este navegador.');" +
                        "} "
        );
    }
}
