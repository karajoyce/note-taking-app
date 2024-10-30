
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;

    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
    exports com.example.demo.view;
    exports com.example.demo.notes;
}