package com.example.insercaoimagens.DAO;

import com.example.insercaoimagens.config.Conn;
import com.example.insercaoimagens.model.Aluno;
import com.example.insercaoimagens.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public CursoDAO() {
    }

    public void inserir(Curso curso) {
        String sql = """
            INSERT INTO curso (id, nome_curso)
            VALUES (?, ?)
        """;

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, curso.getId());
            ps.setString(2, curso.getNomeCurso());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno", e);
        }
    }

    public Long getIdByNome(String nomeCurso) throws SQLException {
        String sql = "SELECT id FROM curso c WHERE c.nome_curso";
        Long idCurso = null;
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                idCurso = rs.getLong("id");

            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }

        return idCurso;
    }

    public List<Curso> listar() {
        String sql = "SELECT * FROM curso";
        List<Curso> listaCurso = new ArrayList<>();

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nomeCurso = rs.getString("nome_curso");
                System.out.println(nomeCurso);
                listaCurso.add(new Curso(id, nomeCurso));
            }
            System.out.println(listaCurso.getFirst());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }

        return listaCurso;
    }
}
