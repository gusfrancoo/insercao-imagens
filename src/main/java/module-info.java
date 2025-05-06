module com.example.insercaoimagens {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.insercaoimagens to javafx.fxml;
    exports com.example.insercaoimagens;
}