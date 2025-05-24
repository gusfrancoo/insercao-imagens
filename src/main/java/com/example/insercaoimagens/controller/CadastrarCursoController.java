package com.example.insercaoimagens.controller;

import com.example.insercaoimagens.DAO.CursoDAO;
import com.example.insercaoimagens.model.Curso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastrarCursoController {
    @FXML
    private TextField textFieldId;
    @FXML private TextField textFieldNome;
    @FXML private Button buttonSalvar;
    @FXML private Button    buttonVoltar;
    @FXML private Label labelResultado;

    private final CursoDAO cursoDAO = new CursoDAO();

    @FXML
    public void initialize() {
        // Inicializações se necessárias
    }

    @FXML
    protected void handleSalvar(ActionEvent event) {
        try {
            long id = Long.parseLong(textFieldId.getText().trim());
            String nome = textFieldNome.getText().trim();

            if (nome.isEmpty()) {
                labelResultado.setText("Informe o nome do curso.");
                return;
            }

            Curso curso = new Curso(id, nome);
            cursoDAO.inserir(curso);
            labelResultado.setText("Curso cadastrado: " + curso.getNomeCurso());
            limparCampos();
        } catch (NumberFormatException e) {
            labelResultado.setText("ID deve ser um número válido.");
        } catch (Exception e) {
            labelResultado.setText("Erro ao salvar curso: " + e.getMessage());
        }
    }

    @FXML
    protected void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/example/insercaoimagens/Cadastro.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setTitle("Tela Inicial");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            labelResultado.setText("Não foi possível voltar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        textFieldId.clear();
        textFieldNome.clear();
    }
}
