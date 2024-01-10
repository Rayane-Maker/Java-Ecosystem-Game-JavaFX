module com.example.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens com.example.javafxproject to javafx.fxml;
    exports com.example.javafxproject;
    exports com.example.javafxproject.GameClasses;
    opens com.example.javafxproject.GameClasses to javafx.fxml;
}