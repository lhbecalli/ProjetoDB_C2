package controller;

import conexion.Conexao;
import model.Aluno;
import model.Emprestimo;
import model.Livro;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerEmprestimo {

    public boolean existe(int idEmprestimo) throws SQLException {
        String sql = "SELECT COUNT(1) total FROM tb_emprestimos WHERE id_emprestimo = " + idEmprestimo;
        return contar(sql) > 0;
    }

    public Emprestimo inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO tb_emprestimos "
                + "(id_aluno, id_livro, data_emprestimo, data_prevista_devolucao, data_devolucao) VALUES ("
                + emprestimo.getAluno().getIdAluno() + ", "
                + emprestimo.getLivro().getIdLivro() + ", "
                + SqlUtil.data(emprestimo.getDataEmprestimo()) + ", "
                + SqlUtil.data(emprestimo.getDataPrevistaDevolucao()) + ", "
                + SqlUtil.data(emprestimo.getDataDevolucao())
                + ") RETURNING id_emprestimo";

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            emprestimo.setIdEmprestimo(rs.getInt(1));
        }
        return emprestimo;
    }

    public Emprestimo atualizar(Emprestimo emprestimo) throws SQLException {
        if (!existe(emprestimo.getIdEmprestimo())) {
            throw new IllegalStateException("Emprestimo nao encontrado.");
        }

        String sql = "UPDATE tb_emprestimos SET "
                + "data_emprestimo = " + SqlUtil.data(emprestimo.getDataEmprestimo()) + ", "
                + "data_prevista_devolucao = " + SqlUtil.data(emprestimo.getDataPrevistaDevolucao()) + ", "
                + "data_devolucao = " + SqlUtil.data(emprestimo.getDataDevolucao())
                + " WHERE id_emprestimo = " + emprestimo.getIdEmprestimo();

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
        return buscar(emprestimo.getIdEmprestimo());
    }

    // Emprestimo nao e referenciado por nenhuma outra tabela, entao a remocao e direta.
    public void remover(int idEmprestimo) throws SQLException {
        if (!existe(idEmprestimo)) {
            throw new IllegalStateException("Emprestimo nao encontrado.");
        }
        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM tb_emprestimos WHERE id_emprestimo = " + idEmprestimo);
        }
    }

    public Emprestimo buscar(int idEmprestimo) throws SQLException {
        String sql = "SELECT e.id_emprestimo, e.data_emprestimo, e.data_prevista_devolucao, e.data_devolucao, "
                + "a.id_aluno, a.nome, a.matricula, a.turma, a.email, "
                + "l.id_livro, l.titulo, l.autor, l.genero, l.ano_publicacao, l.quantidade_exemplares "
                + "FROM tb_emprestimos e "
                + "JOIN tb_alunos a ON a.id_aluno = e.id_aluno "
                + "JOIN tb_livros l ON l.id_livro = e.id_livro "
                + "WHERE e.id_emprestimo = " + idEmprestimo;

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return montar(rs);
            }
        }
        return null;
    }

    public List<Emprestimo> listar() throws SQLException {
        String sql = "SELECT e.id_emprestimo, e.data_emprestimo, e.data_prevista_devolucao, e.data_devolucao, "
                + "a.id_aluno, a.nome, a.matricula, a.turma, a.email, "
                + "l.id_livro, l.titulo, l.autor, l.genero, l.ano_publicacao, l.quantidade_exemplares "
                + "FROM tb_emprestimos e "
                + "JOIN tb_alunos a ON a.id_aluno = e.id_aluno "
                + "JOIN tb_livros l ON l.id_livro = e.id_livro "
                + "ORDER BY e.id_emprestimo";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                emprestimos.add(montar(rs));
            }
        }
        return emprestimos;
    }

    private Emprestimo montar(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno(rs.getInt("id_aluno"), rs.getString("nome"), rs.getString("matricula"),
                rs.getString("turma"), rs.getString("email"));

        int ano = rs.getInt("ano_publicacao");
        Integer anoPublicacao = rs.wasNull() ? null : ano;
        Livro livro = new Livro(rs.getInt("id_livro"), rs.getString("titulo"), rs.getString("autor"),
                rs.getString("genero"), anoPublicacao, rs.getInt("quantidade_exemplares"));

        LocalDate dataDevolucao = rs.getDate("data_devolucao") == null
                ? null : rs.getDate("data_devolucao").toLocalDate();

        return new Emprestimo(rs.getInt("id_emprestimo"), aluno, livro,
                rs.getDate("data_emprestimo").toLocalDate(),
                rs.getDate("data_prevista_devolucao").toLocalDate(),
                dataDevolucao);
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
