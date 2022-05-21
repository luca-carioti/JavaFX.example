module com.luca.bibliotecac {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.poi.ooxml;
    requires java.sql;

    opens com.luca.bibliotecac to javafx.fxml;
    exports com.luca.bibliotecac;
    exports com.luca.entities;
    exports com.luca.repository;
    exports com.luca.connector;
    exports com.luca.lettore;
}