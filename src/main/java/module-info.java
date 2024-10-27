
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    //requires richtextfx.fat;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires org.fxmisc.flowless;


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