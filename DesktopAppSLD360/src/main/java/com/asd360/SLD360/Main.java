package com.asd360.SLD360;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asd360/SLD360/rool_layout.fxml"));
        Parent root = loader.load();

        BaseController controller = loader.getController();
        controller.loadView("index.fxml");

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("SLD360 - Salud Integral");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}