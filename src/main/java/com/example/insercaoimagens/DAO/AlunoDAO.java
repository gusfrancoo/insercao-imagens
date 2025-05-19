package com.example.insercaoimagens.DAO;

import com.example.insercaoimagens.config.Conn;
import com.example.insercaoimagens.model.Aluno;
import com.example.insercaoimagens.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {


    public void inserir(Aluno aluno) {
        String sql = """
            INSERT INTO alunos (matricula, nome, maioridade, curso, sexo)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT (matricula) DO NOTHING
        """;

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, aluno.getMatricula());
            ps.setString(2, aluno.getNome());
            ps.setBoolean(3, aluno.isMaioridade());
            ps.setString(4, aluno.getCurso().getNomeCurso());
            ps.setString(5, aluno.getSexo());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno", e);
        }
    }


    public List<Aluno> listar() {
        String sql = "SELECT matricula, nome, maioridade, curso, sexo FROM alunos";
        List<Aluno> lista = new ArrayList<>();

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long matricula = rs.getLong("matricula");
                String nome = rs.getString("nome");
                boolean maior = rs.getBoolean("maioridade");
                CursoDAO cursoDAO = new CursoDAO();
                Long idCurso = cursoDAO.getIdByNome(rs.getString("curso"));
                Curso curso = new Curso(idCurso, rs.getString("curso"));

                String sexo = rs.getString("sexo");

                lista.add(new Aluno(matricula, nome, maior, curso, sexo));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }

        return lista;
    }


//    public Aluno buscarPorMatricula(Long matricula) {
//        String sql = "SELECT matricula, nome, maioridade, curso, sexo FROM alunos WHERE matricula = ?";
//
//        try (Connection conn = Conn.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setLong(1, matricula);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    String nome = rs.getString("nome");
//                    boolean maior = rs.getBoolean("maioridade");
////                    Curso curso = Curso.valueOf(rs.getString("curso"));
//                    String sexo = rs.getString("sexo");
//                    return new Aluno(matricula, nome, maior, '', sexo);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Erro ao buscar aluno", e);
//        }
//
//        return null;
//    }

//
//    public void atualizar(Aluno aluno) {
//        String sql = """
//            UPDATE alunos
//            SET nome = ?, maioridade = ?, curso = ?, sexo = ?
//            WHERE matricula = ?
//        """;
//
//        try (Connection conn = Conn.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, aluno.getNome());
//            ps.setBoolean(2, aluno.isMaioridade());
//            ps.setString(3, aluno.getCurso().name());
//            ps.setString(4, aluno.getSexo());
//            ps.setLong(5, aluno.getMatricula());
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Erro ao atualizar aluno", e);
//        }
//    }
//
//    public void deletar(Long matricula) {
//        String sql = "DELETE FROM alunos WHERE matricula = ?";
//
//        try (Connection conn = Conn.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setLong(1, matricula);
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Erro ao deletar aluno", e);
//        }
//    }
}
