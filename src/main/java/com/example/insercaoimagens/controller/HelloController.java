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
import javafx.scene.control.*;

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
//        Curso curso = new Curso('1', "ADS");
//        Curso curso2 = new Curso('2', "CC");
//        Curso curso3 = new Curso('3', "ARQ");
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
}
