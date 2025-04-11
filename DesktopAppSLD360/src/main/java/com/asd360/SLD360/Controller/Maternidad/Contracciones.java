package com.asd360.SLD360.Maternidad;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Contracciones {

    @FXML
    private Label ultimaContraccionLabel;

    @FXML
    private Label tiempoTranscurridoLabel;

    @FXML
    private Label totalContraccionesLabel;

    @FXML
    private Button detenerButton;

    @FXML
    private ListView<String> historialList;

    @FXML
    private VBox historialCard;

    private List<Contraccion> contracciones = new ArrayList<>();
    private LocalDateTime ultimaContraccion;
    private long inicioContraccion;
    private boolean contrayendo = false;
    private AnimationTimer temporizador;

    @FXML
    public void initialize() {
        temporizador = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (contrayendo) {
                    long tiempoTranscurrido = (System.currentTimeMillis() - inicioContraccion) / 1000;
                    tiempoTranscurridoLabel.setText("Tiempo Transcurrido: " + tiempoTranscurrido + " segundos");
                }
            }
        };
    }

    @FXML
    private void registrarContraccion() {
        inicioContraccion = System.currentTimeMillis();
        contrayendo = true;
        detenerButton.setDisable(false);
        temporizador.start();
    }

    @FXML
    private void detenerContraccion() {
        if (contrayendo) {
            temporizador.stop();
            contrayendo = false;
            detenerButton.setDisable(true);

            LocalDateTime ahora = LocalDateTime.now();
            long duracion = (System.currentTimeMillis() - inicioContraccion) / 1000;
            long intervalo = ultimaContraccion != null ? (ahora.toEpochSecond(java.time.ZoneOffset.UTC) - ultimaContraccion.toEpochSecond(java.time.ZoneOffset.UTC)) : 0;

            contracciones.add(new Contraccion(ahora, duracion, intervalo));
            ultimaContraccion = ahora;

            actualizarUI();
        }
    }

    @FXML
    private void reiniciarContador() {
        contracciones.clear();
        ultimaContraccion = null;
        tiempoTranscurridoLabel.setText("Tiempo Transcurrido: 0 segundos");
        totalContraccionesLabel.setText("Total de Contracciones: 0");
        historialList.getItems().clear();
        historialCard.setVisible(false);
        temporizador.stop();
        contrayendo = false;
        detenerButton.setDisable(true);
    }

    private void actualizarUI() {
        ultimaContraccionLabel.setText("Última Contracción: " + ultimaContraccion.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        totalContraccionesLabel.setText("Total de Contracciones: " + contracciones.size());

        historialList.getItems().clear();
        for (int i = 0; i < contracciones.size(); i++) {
            Contraccion c = contracciones.get(i);
            String item = "Contracción " + (i + 1) + "\n" +
                    "Inicio: " + c.getInicio().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n" +
                    "Duración: " + c.getDuracion() + " segundos\n" +
                    (i > 0 ? "Intervalo con la anterior: " + c.getIntervalo() + " segundos" : "");
            historialList.getItems().add(item);
        }

        historialCard.setVisible(!contracciones.isEmpty());
    }

    @FXML
    private void goHome() {
        // Navegar a la pantalla de inicio
    }

    @FXML
    private void goBack() {
        // Navegar a la pantalla anterior
    }

    private static class Contraccion {
        private LocalDateTime inicio;
        private long duracion;
        private long intervalo;

        public Contraccion(LocalDateTime inicio, long duracion, long intervalo) {
            this.inicio = inicio;
            this.duracion = duracion;
            this.intervalo = intervalo;
        }

        public LocalDateTime getInicio() {
            return inicio;
        }

        public long getDuracion() {
            return duracion;
        }

        public long getIntervalo() {
            return intervalo;
        }
    }
}