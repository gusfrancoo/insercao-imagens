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
        String sql = "SELECT id FROM curso c WHERE c.nome_curso = ?";
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeCurso);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                } else {
                    return null;  // ou lançar exceção se preferir
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id de curso por nome", e);
        }
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



    public void atualizar(Curso curso) {
        String sql = """
            UPDATE curso
               SET nome_curso = ?
             WHERE id = ?
        """;

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getNomeCurso());
            ps.setLong(2, curso.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso", e);
        }
    }

}
