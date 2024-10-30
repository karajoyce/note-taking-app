module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    exports model;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}