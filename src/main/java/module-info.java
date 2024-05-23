module co.edu.uniquindio.proyectofinalprogramacion3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.logging;
    requires org.mapstruct;

    requires com.rabbitmq.client;


    opens co.edu.uniquindio.proyectofinalprogramacion3 to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalprogramacion3;


    exports co.edu.uniquindio.proyectofinalprogramacion3.viewController;
    opens co.edu.uniquindio.proyectofinalprogramacion3.viewController to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalprogramacion3.controller;
    exports co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto;
    exports co.edu.uniquindio.proyectofinalprogramacion3.mapping.mappers;
    exports co.edu.uniquindio.proyectofinalprogramacion3.model;
    opens co.edu.uniquindio.proyectofinalprogramacion3.controller to javafx.fxml;
}