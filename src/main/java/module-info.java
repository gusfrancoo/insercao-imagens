module com.example.insercaoimagens {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.insercaoimagens to javafx.fxml;
    exports com.example.insercaoimagens;
    exports com.example.insercaoimagens.controller;
    opens com.example.insercaoimagens.controller to javafx.fxml;
}