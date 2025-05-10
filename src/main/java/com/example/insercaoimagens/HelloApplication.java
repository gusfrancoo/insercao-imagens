package com.example.insercaoimagens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("Cadastro.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root, 650, 600);

        stage.setTitle("Restaurante Virtual – Cadastro");
        stage.setScene(scene);

        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
