
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports notes;
}

/*
open module notes {
        requires javafx.controls;
        requires javafx.fxml;


        //opens notes to javafx.fxml;
        exports notes;

}
*/