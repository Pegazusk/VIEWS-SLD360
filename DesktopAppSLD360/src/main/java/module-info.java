module com.asd360.asd360 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.bytedeco.javacv;
    requires javafx.swing;
    requires javafx.web;
    requires org.bytedeco.opencv;
    requires java.mail;
    requires com.google.gson;
    requires unirest.java;
    requires java.sql;

    opens com.asd360.SLD360 to javafx.fxml;
    exports com.asd360.SLD360;
    exports com.asd360.SLD360.Maternidad;
    opens com.asd360.SLD360.Maternidad to javafx.fxml;
    exports com.asd360.SLD360.VidaSexual;
    opens com.asd360.SLD360.VidaSexual to javafx.fxml;
    // Elimina o corrige estas líneas:
    // exports com.asd360.SLD360.Discapacidad;
    // opens com.asd360.SLD360.Discapacidad to javafx.fxml;

    exports com.asd360.SLD360.ComunidadRural;
    opens com.asd360.SLD360.ComunidadRural to javafx.fxml;
    exports com.asd360.SLD360.AdultosMayores;
    opens com.asd360.SLD360.AdultosMayores to javafx.fxml;
    exports com.asd360.SLD360.Adolescentes;
    opens com.asd360.SLD360.Adolescentes to javafx.fxml;
    exports com.asd360.SLD360.Controller.Login;
    opens com.asd360.SLD360.Controller.Login to javafx.fxml;

    // Agrega estas líneas para el paquete del controlador:
    exports com.asd360.SLD360.Controller.Discapacidad;
    opens com.asd360.SLD360.Controller.Discapacidad to javafx.fxml;
}