package com.asd360.SLD360.Controller.ComunidadRural;

import com.asd360.SLD360.BaseController;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class AtencionMovilController extends BaseController {

    @FXML
    private void descargarNahuatl(ActionEvent event) {
        System.out.println("Descargar Guía en Náhuatl");
    }

    @FXML
    private void descargarMaya(ActionEvent event) {
        System.out.println("Descargar Guía en Maya");
    }

    @FXML
    private void descargarZapoteco(ActionEvent event) {
        System.out.println("Descargar Guía en Zapoteco");
    }

    @FXML
    private void descargarMixteco(ActionEvent event) {
        System.out.println("Descargar Guía en Mixteco");
    }

    @FXML
    private void descargarTodasLasGuias(ActionEvent event) {
        System.out.println("Descargar todas las guías (ZIP)");
    }
}