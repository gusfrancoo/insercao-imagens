package com.example.insercaoimagens.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Conn {
    private static final String URL     = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA   = "7004";

    static {
        try {
            criarTabelaSeNaoExistir();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(
                    "Falha ao inicializar esquema de banco: " + e.getMessage()
            );
        }
    }


    private static void criarTabelaSeNaoExistir() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS alunos (
                matricula   BIGINT        PRIMARY KEY,
                nome        VARCHAR(150)  NOT NULL,
                maioridade  BOOLEAN       NOT NULL,
                curso       VARCHAR(20)   NOT NULL,
                sexo        VARCHAR(10)   NOT NULL
            );
        """;
        String sql2 = """
            CREATE TABLE IF NOT EXISTS curso (
                id   BIGINT        PRIMARY KEY,
                nome_curso        VARCHAR(150)  NOT NULL
            );
        """;

        try (Connection conn = getConnection()) {
             Statement  stmt = conn.createStatement();
             Statement  stmt2 = conn.createStatement();
            stmt.execute(sql);
            stmt2.execute(sql2);
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
