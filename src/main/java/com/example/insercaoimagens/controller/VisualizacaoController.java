package com.example.insercaoimagens.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class VisualizacaoController {

    @FXML private ListView<HBox> listViewItems;

    /**
     * Este método é chamado pelo HelloController para inserir
     * um único item (imagem + texto) no ListView.
     */
    public void addItem(Image img, String texto) {
        // monta o HBox
        ImageView iv = new ImageView(img);
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        iv.setPreserveRatio(true);

        Label lbl = new Label(texto);
        lbl.setWrapText(true);
        lbl.setStyle("-fx-font-size:14px;");

        HBox cell = new HBox(10, iv, lbl);

        // coloca só esse cell na lista
        listViewItems.setItems(FXCollections.observableArrayList(cell));
        // garante que o ListView renderize o HBox como célula
        listViewItems.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : item);
            }
        });
    }

    @FXML
    protected void handleVoltar(javafx.event.ActionEvent event) throws IOException {
        javafx.stage.Stage stage = (javafx.stage.Stage) listViewItems.getScene().getWindow();
        Parent cadastro = FXMLLoader.load(
                getClass().getResource("/com/example/insercaoimagens/Cadastro.fxml")
        );
        stage.setScene(new Scene(cadastro, 600, 450));
        stage.setTitle("Restaurante Virtual – Cadastro");
    }
}
