
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;
    requires javafx.graphics;

    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.model to com.google.gson; // Allow Gson to access private fields in this package

    exports com.example.demo;
    exports com.example.demo.model;
    exports com.example.demo.view;
    exports com.example.demo.notes;
    exports com.example.demo.controller;
}