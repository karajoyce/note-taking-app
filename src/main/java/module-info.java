module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires com.google.gson;
    requires java.desktop;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports model;
    exports view;
}