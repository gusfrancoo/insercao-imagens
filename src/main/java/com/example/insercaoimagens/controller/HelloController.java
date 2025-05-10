package com.example.insercaoimagens.controller;

import com.example.insercaoimagens.model.Prato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloController {

    @FXML private ComboBox<Prato> comboBoxPratos;
    @FXML private CheckBox checkBoxBebida;
    @FXML private CheckBox checkBoxSobremesa;
    @FXML private TextArea textAreaObservacoes;
    @FXML private Button buttonSelecionarImagem;
    @FXML private ImageView imageViewPrato;
    @FXML private Button buttonCadastrar;
    @FXML private Button buttonVisualizar;      // <— injeção do botão novo
    @FXML private Label labelResultado;

    private File imagemSelecionada;

    @FXML
    public void initialize() {
        comboBoxPratos.setItems(Prato.getOpcoesPadrao());
    }

    @FXML
    protected void handleSelecionarImagem(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecione uma imagem");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File file = chooser.showOpenDialog(buttonSelecionarImagem.getScene().getWindow());
        if (file != null) {
            imagemSelecionada = file;
            imageViewPrato.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    protected void handleCadastrar(ActionEvent event) {
        Prato prato = comboBoxPratos.getValue();
        boolean bebida = checkBoxBebida.isSelected();
        boolean sobremesa = checkBoxSobremesa.isSelected();
        String obs = textAreaObservacoes.getText().trim();
        String nomeImg = imagemSelecionada != null ? imagemSelecionada.getName() : "Nenhuma";

        String resumo = String.format(
                "Prato: %s\nBebida: %s\nSobremesa: %s\nObs.: %s\nImagem: %s",
                prato,
                bebida ? "Sim" : "Não",
                sobremesa ? "Sim" : "Não",
                obs.isEmpty() ? "-" : obs,
                nomeImg
        );
        labelResultado.setText(resumo);
    }

    @FXML
    protected void handleVisualizarDados(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/insercaoimagens/Visualizacao.fxml")
        );
        Parent root = loader.load();

        VisualizacaoController vc = loader.getController();
        vc.addItem(imageViewPrato.getImage(), labelResultado.getText());

        Stage stage = (Stage) buttonVisualizar.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 450));
        stage.setTitle("Restaurante Virtual – Visualização de Dados");
    }
}
