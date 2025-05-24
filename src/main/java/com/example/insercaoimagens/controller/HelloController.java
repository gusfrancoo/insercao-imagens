// src/main/java/com/example/insercaoimagens/controller/HelloController.java
package com.example.insercaoimagens.controller;

import com.example.insercaoimagens.DAO.AlunoDAO;
import com.example.insercaoimagens.DAO.CursoDAO;
import com.example.insercaoimagens.config.Conn;
import com.example.insercaoimagens.model.Aluno;
import com.example.insercaoimagens.model.Curso;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML private TextField textFieldMatricula;
    @FXML private TextField textFieldNome;
    @FXML private CheckBox checkBoxMaioridade;
    @FXML private ComboBox<Curso> comboBoxCurso;
    @FXML private ComboBox<String> comboBoxSexo;
    @FXML private Button buttonCadastrar;
    @FXML private Label labelResultado;

    public Conn connection;

    @FXML
    public void initialize() throws SQLException {
        Conn conn = new Conn();
        List<Curso> listaCurso = new ArrayList<>();
        CursoDAO cursoDAO = new CursoDAO();

//        DESCOMENTAR PARA INSERÇÃO
//        Curso curso = new Curso('4', "TI");
//        Curso curso2 = new Curso('5', "MED");
//        Curso curso3 = new Curso('6', "IA");
//        cursoDAO.inserir(curso);
//        cursoDAO.inserir(curso2);
//        cursoDAO.inserir(curso3);

        listaCurso = cursoDAO.listar();
        System.out.println(listaCurso);
        comboBoxCurso.setItems(FXCollections.observableArrayList(listaCurso));
        comboBoxSexo.setItems(FXCollections.observableArrayList("Masculino", "Feminino", "Outro"));
        connection = new Conn();

    }

    @FXML
    protected void handleCadastrar(ActionEvent event) {
        try {
            Long matricula = Long.parseLong(textFieldMatricula.getText().trim());
            String nome = textFieldNome.getText().trim();
            boolean maioridade = checkBoxMaioridade.isSelected();
            Curso curso = comboBoxCurso.getValue();
            String sexo = comboBoxSexo.getValue();

            if (nome.isEmpty() || curso == null || sexo == null) {
                labelResultado.setText("Preencha todos os campos obrigatórios.");
                return;
            }

            Aluno aluno = new Aluno(matricula, nome, maioridade, curso, sexo);

            AlunoDAO alunoDao = new AlunoDAO();

            String resumo = String.format(
                    "Matrícula: %d\nNome: %s\nMaioridade: %s\nCurso: %s\nSexo: %s",
                    aluno.getMatricula(),
                    aluno.getNome(),
                    aluno.isMaioridade() ? "Sim" : "Não",
                    aluno.getCurso(),
                    aluno.getSexo()
            );

            labelResultado.setText(resumo);
            alunoDao.inserir(aluno);
            limparCampos();

        } catch (NumberFormatException e) {
            labelResultado.setText("Matrícula deve ser um número válido.");
        }
    }

    private void limparCampos() {
        textFieldMatricula.clear();
        textFieldNome.clear();

        checkBoxMaioridade.setSelected(false);

        comboBoxCurso.getSelectionModel().clearSelection();
        comboBoxCurso.setValue(null);

        comboBoxSexo.getSelectionModel().clearSelection();
        comboBoxSexo.setValue(null);
    }

    @FXML
    protected void handleVerAlunos(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/insercaoimagens/view-aluno.fxml")
            );
            Parent root = loader.load();

            // Obtém o Stage atual
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            // Troca a cena
            stage.setTitle("Gerenciar Alunos");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            labelResultado.setText("Não foi possível abrir a tela de alunos.");
        }
    }

    @FXML
    protected void handleCadastrarCurso(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/insercaoimagens/cadastrar-curso.fxml")
            );
            Parent root = loader.load();

            // Obtém o Stage atual
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            // Troca a cena
            stage.setTitle("Cadastrar curso");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            labelResultado.setText("Não foi possível abrir a tela de cadastro de cursos.");
        }
    }
    @FXML
    protected void handleVerCursos(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/insercaoimagens/view-curso.fxml")
            );
            Parent root = loader.load();

            // Obtém o Stage atual
            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            // Troca a cena
            stage.setTitle("Visualizar cursos");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            labelResultado.setText("Não foi possível abrir a tela de visualização de cursos.");
        }
    }
}
