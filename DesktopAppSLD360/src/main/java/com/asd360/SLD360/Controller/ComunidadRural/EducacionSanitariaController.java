package com.asd360.SLD360.ComunidadRural;

import com.asd360.SLD360.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.Frame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;

public class EducacionSanitariaController extends BaseController {

    @FXML
    private ImageView videoStream;

    @FXML
    private Button toggleCameraButton;

    private OpenCVFrameGrabber grabber;
    private boolean cameraActive = false;

    @FXML
    private void hacerLlamada(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("tel:+123456789"));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("No se pudo iniciar la llamada.");
        }
    }

    @FXML
    private void abrirWhatsApp(ActionEvent event) {
        String telefono = "4772299615";
        String mensaje = "Hola, necesito asistencia médica.";
        String url = "https://wa.me/" + telefono + "?text=" + mensaje.replace(" ", "%20");

        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("No se pudo abrir WhatsApp.");
        }
    }

    @FXML
    private void toggleCamara(ActionEvent event) {
        if (!cameraActive) {
            try {
                grabber = new OpenCVFrameGrabber(0);
                grabber.start();

                Thread cameraThread = new Thread(() -> {
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    while (cameraActive) {
                        try {
                            Frame frame = grabber.grab();
                            if (frame != null) {
                                BufferedImage bufferedImage = converter.getBufferedImage(frame);
                                Image image = javafx.embed.swing.SwingFXUtils.toFXImage(bufferedImage, null);
                                javafx.application.Platform.runLater(() -> {
                                    videoStream.setImage(image);
                                });
                            }
                        } catch (FrameGrabber.Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                cameraActive = true;
                toggleCameraButton.setText("Apagar Cámara");
                cameraThread.start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
                mostrarError("No se pudo acceder a la cámara.");
            }
        } else {
            cameraActive = false;
            toggleCameraButton.setText("Encender Cámara");
            if (grabber != null) {
                try {
                    grabber.stop();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
            videoStream.setImage(null);
        }
    }
}