package com.example.insercaoimagens.controller;

import com.example.insercaoimagens.DAO.AlunoDAO;
import com.example.insercaoimagens.DAO.CursoDAO;
import com.example.insercaoimagens.model.Aluno;
import com.example.insercaoimagens.model.Curso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AlunoController {

    @FXML private ListView<Aluno> listAlunos;
    @FXML private TextField txtMatricula;
    @FXML private TextField txtNome;
    @FXML private CheckBox chkMaioridade;
    @FXML private ComboBox<Curso> comboCurso;
    @FXML private ComboBox<String> comboSexo;

    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final CursoDAO cursoDAO = new CursoDAO();
    private ObservableList<Aluno> alunos;
    private Aluno selecionado;

    @FXML
    public void initialize() {
        // popula cursos e sexo
        comboCurso.setItems(FXCollections.observableArrayList(cursoDAO.listar()));
        comboSexo.setItems(FXCollections.observableArrayList("Masculino","Feminino","Outro"));

        // carrega alunos
        alunos = FXCollections.observableArrayList(alunoDAO.listar());
        listAlunos.setItems(alunos);

        // mostra "matrícula – nome" em cada célula
        listAlunos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Aluno a, boolean empty) {
                super.updateItem(a, empty);
                setText(empty || a == null ? null
                        : a.getMatricula() + " – " + a.getNome());
            }
        });

        // preenche formulário ao selecionar
        listAlunos.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, novo) -> {
                    selecionado = novo;
                    if (novo != null) {
                        txtMatricula.setText(novo.getMatricula().toString());
                        txtNome.setText(novo.getNome());
                        chkMaioridade.setSelected(novo.isMaioridade());
                        comboCurso.getSelectionModel().select(novo.getCurso());
                        comboSexo.getSelectionModel().select(novo.getSexo());
                    }
                });
    }

    @FXML
    private void onNovo() {
        listAlunos.getSelectionModel().clearSelection();
        selecionado = null;
        limparCampos();
    }

    @FXML
    private void onSalvar() {
        Long mat = Long.parseLong(txtMatricula.getText());
        String nome = txtNome.getText();
        boolean maior = chkMaioridade.isSelected();
        Curso curso = comboCurso.getValue();
        String sexo = comboSexo.getValue();

        if (selecionado == null) {
            Aluno novo = new Aluno(mat, nome, maior, curso, sexo);
            alunoDAO.inserir(novo);
            alunos.add(novo);
        } else {
            selecionado.setMatricula(mat);
            selecionado.setNome(nome);
            selecionado.setMaioridade(maior);
            selecionado.setCurso(curso);
            selecionado.setSexo(sexo);
            alunoDAO.atualizar(selecionado);
            listAlunos.refresh();
        }
        limparCampos();
    }

    @FXML
    private void onLimpar() {
        // Se houver um aluno selecionado, deleta do banco e da lista
        if (selecionado != null) {
            alunoDAO.deletar(selecionado.getMatricula());
            alunos.remove(selecionado);
            selecionado = null;
        }
        // Limpa seleção e campos da UI
        listAlunos.getSelectionModel().clearSelection();
        limparCampos();
    }

    @FXML
    private void onEditar() {
        if (selecionado != null) {
            txtNome.requestFocus();
        }
    }

    @FXML
    private void onDeletar() {
        if (selecionado != null) {
            alunoDAO.deletar(selecionado.getMatricula());
            alunos.remove(selecionado);
            limparCampos();
        }
    }

    private void limparCampos() {
        txtMatricula.clear();
        txtNome.clear();
        chkMaioridade.setSelected(false);
        comboCurso.getSelectionModel().clearSelection();
        comboSexo.getSelectionModel().clearSelection();
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
        }
    }
}
