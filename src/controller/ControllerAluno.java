package controller;

import conexion.Conexao;
import model.Aluno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerAluno {

    public boolean existe(int idAluno) throws SQLException {
        String sql = "SELECT COUNT(1) total FROM alunos WHERE id_aluno = " + idAluno;
        return contar(sql) > 0;
    }

    public boolean existeMatricula(String matricula, int idIgnorado) throws SQLException {
        String sql = "SELECT COUNT(1) total FROM alunos WHERE matricula = " + SqlUtil.texto(matricula)
                + " AND id_aluno <> " + idIgnorado;
        return contar(sql) > 0;
    }

    public int contarEmprestimos(int idAluno) throws SQLException {
        String sql = "SELECT COUNT(1) total FROM emprestimos WHERE id_aluno = " + idAluno;
        return contar(sql);
    }

    public Aluno inserir(Aluno aluno) throws SQLException {
        if (existeMatricula(aluno.getMatricula(), 0)) {
            throw new IllegalStateException("Ja existe um aluno com essa matricula.");
        }

        String sql = "INSERT INTO alunos (nome, matricula, turma, email) VALUES ("
                + SqlUtil.texto(aluno.getNome()) + ", "
                + SqlUtil.texto(aluno.getMatricula()) + ", "
                + SqlUtil.texto(aluno.getTurma()) + ", "
                + SqlUtil.texto(aluno.getEmail())
                + ") RETURNING id_aluno";

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            aluno.setIdAluno(rs.getInt(1));
        }
        return aluno;
    }

    public Aluno atualizar(Aluno aluno) throws SQLException {
        if (!existe(aluno.getIdAluno())) {
            throw new IllegalStateException("Aluno nao encontrado.");
        }
        if (existeMatricula(aluno.getMatricula(), aluno.getIdAluno())) {
            throw new IllegalStateException("Ja existe outro aluno com essa matricula.");
        }

        String sql = "UPDATE alunos SET "
                + "nome = " + SqlUtil.texto(aluno.getNome()) + ", "
                + "matricula = " + SqlUtil.texto(aluno.getMatricula()) + ", "
                + "turma = " + SqlUtil.texto(aluno.getTurma()) + ", "
                + "email = " + SqlUtil.texto(aluno.getEmail())
                + " WHERE id_aluno = " + aluno.getIdAluno();

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
        return buscar(aluno.getIdAluno());
    }

    // Se o aluno tem emprestimos, so remove quando apagarEmprestimos for true.
    public void remover(int idAluno, boolean apagarEmprestimos) throws SQLException {
        if (!existe(idAluno)) {
            throw new IllegalStateException("Aluno nao encontrado.");
        }
        if (contarEmprestimos(idAluno) > 0 && !apagarEmprestimos) {
            throw new IllegalStateException("Aluno possui emprestimos e nao pode ser removido.");
        }

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM emprestimos WHERE id_aluno = " + idAluno);
                st.executeUpdate("DELETE FROM alunos WHERE id_aluno = " + idAluno);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public Aluno buscar(int idAluno) throws SQLException {
        String sql = "SELECT id_aluno, nome, matricula, turma, email FROM alunos WHERE id_aluno = " + idAluno;

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return montar(rs);
            }
        }
        return null;
    }

    public List<Aluno> listar() throws SQLException {
        String sql = "SELECT id_aluno, nome, matricula, turma, email FROM alunos ORDER BY id_aluno";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                alunos.add(montar(rs));
            }
        }
        return alunos;
    }

    private Aluno montar(ResultSet rs) throws SQLException {
        return new Aluno(rs.getInt("id_aluno"), rs.getString("nome"), rs.getString("matricula"),
                rs.getString("turma"), rs.getString("email"));
    }

    private int contar(String sql) throws SQLException {
        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt("total");
        }
    }
}
