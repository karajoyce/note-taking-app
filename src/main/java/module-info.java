module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
//    exports controller;
    exports model;
    exports view;
}