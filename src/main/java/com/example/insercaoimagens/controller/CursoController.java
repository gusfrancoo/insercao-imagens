package com.example.insercaoimagens.controller;

import com.example.insercaoimagens.DAO.CursoDAO;
import com.example.insercaoimagens.model.Curso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CursoController {

    @FXML private ListView<Curso> listCursos;
    @FXML private TextField txtId;
    @FXML private TextField txtNomeCurso;

    private final CursoDAO cursoDAO = new CursoDAO();
    private ObservableList<Curso> cursos;
    private Curso selecionado;

    @FXML
    public void initialize() {
        // carrega lista de cursos
        cursos = FXCollections.observableArrayList(cursoDAO.listar());
        listCursos.setItems(cursos);

        // mostra "id – nome" em cada célula
        listCursos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Curso c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null
                        : c.getId() + " – " + c.getNomeCurso());
            }
        });

        // preenche formulário ao selecionar
        listCursos.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, novo) -> {
                    selecionado = novo;
                    if (novo != null) {
                        txtId.setText(String.valueOf(novo.getId()));
                        txtNomeCurso.setText(novo.getNomeCurso());
                    }
                });
    }

    @FXML
    private void onNovo() {
        listCursos.getSelectionModel().clearSelection();
        selecionado = null;
        limparCampos();
    }

    @FXML
    private void onSalvar() {
        long id = Long.parseLong(txtId.getText().trim());
        String nome = txtNomeCurso.getText().trim();

        if (selecionado == null) {
            // inserir novo
            Curso novo = new Curso(id, nome);
            cursoDAO.inserir(novo);
            cursos.add(novo);
        } else {
            // atualizar existente
            selecionado.setId(id);
            selecionado.setNomeCurso(nome);
            cursoDAO.atualizar(selecionado);
            listCursos.refresh();
        }
        limparCampos();
    }

    @FXML
    private void onLimpar() {
        if (selecionado != null) {
            cursoDAO.deletar(selecionado.getId());
            cursos.remove(selecionado);
            selecionado = null;
        }
        listCursos.getSelectionModel().clearSelection();
        limparCampos();
    }

    @FXML
    private void onEditar() {
        if (selecionado != null) {
            txtNomeCurso.requestFocus();
        }
    }

    @FXML
    private void onDeletar() {
        if (selecionado != null) {
            cursoDAO.deletar(selecionado.getId());
            cursos.remove(selecionado);
            limparCampos();
        }
    }

    private void limparCampos() {
        txtId.clear();
        txtNomeCurso.clear();
    }

    @FXML
    protected void onVoltar(ActionEvent event) {
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
        }
    }
}
