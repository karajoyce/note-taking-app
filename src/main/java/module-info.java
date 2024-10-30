module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
//    exports controller;
    exports com.example.demo.model;
    exports com.example.demo.view;
}