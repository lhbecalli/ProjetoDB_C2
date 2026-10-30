package controller;

import conexion.Conexao;
import model.Livro;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerLivro {

    public boolean existe(int idLivro) throws SQLException {
        String sql = "SELECT COUNT(1) total FROM livros WHERE id_livro = " + idLivro;
        return contar(sql) > 0;
    }

    public int contarEmprestimos(int idLivro) throws SQLException {
        String sql = "SELECT COUNT(1) total FROM emprestimos WHERE id_livro = " + idLivro;
        return contar(sql);
    }

    public Livro inserir(Livro livro) throws SQLException {
        validar(livro);

        String sql = "INSERT INTO livros (titulo, autor, genero, ano_publicacao, quantidade_exemplares) VALUES ("
                + SqlUtil.texto(livro.getTitulo()) + ", "
                + SqlUtil.texto(livro.getAutor()) + ", "
                + SqlUtil.texto(livro.getGenero()) + ", "
                + SqlUtil.numero(livro.getAnoPublicacao()) + ", "
                + livro.getQuantidadeExemplares()
                + ") RETURNING id_livro";

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            livro.setIdLivro(rs.getInt(1));
        }
        return livro;
    }

    public Livro atualizar(Livro livro) throws SQLException {
        if (!existe(livro.getIdLivro())) {
            throw new IllegalStateException("Livro nao encontrado.");
        }
        validar(livro);

        String sql = "UPDATE livros SET "
                + "titulo = " + SqlUtil.texto(livro.getTitulo()) + ", "
                + "autor = " + SqlUtil.texto(livro.getAutor()) + ", "
                + "genero = " + SqlUtil.texto(livro.getGenero()) + ", "
                + "ano_publicacao = " + SqlUtil.numero(livro.getAnoPublicacao()) + ", "
                + "quantidade_exemplares = " + livro.getQuantidadeExemplares()
                + " WHERE id_livro = " + livro.getIdLivro();

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
        return buscar(livro.getIdLivro());
    }

    // Se o livro tem emprestimos, so remove quando apagarEmprestimos for true.
    public void remover(int idLivro, boolean apagarEmprestimos) throws SQLException {
        if (!existe(idLivro)) {
            throw new IllegalStateException("Livro nao encontrado.");
        }
        if (contarEmprestimos(idLivro) > 0 && !apagarEmprestimos) {
            throw new IllegalStateException("Livro possui emprestimos e nao pode ser removido.");
        }

        try (Connection conn = Conexao.getConexao()) {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("DELETE FROM emprestimos WHERE id_livro = " + idLivro);
                st.executeUpdate("DELETE FROM livros WHERE id_livro = " + idLivro);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public Livro buscar(int idLivro) throws SQLException {
        String sql = "SELECT id_livro, titulo, autor, genero, ano_publicacao, quantidade_exemplares "
                + "FROM livros WHERE id_livro = " + idLivro;

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return montar(rs);
            }
        }
        return null;
    }

    public List<Livro> listar() throws SQLException {
        String sql = "SELECT id_livro, titulo, autor, genero, ano_publicacao, quantidade_exemplares "
                + "FROM livros ORDER BY id_livro";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                livros.add(montar(rs));
            }
        }
        return livros;
    }

    private void validar(Livro livro) {
        if (livro.getQuantidadeExemplares() < 0) {
            throw new IllegalStateException("A quantidade de exemplares nao pode ser negativa.");
        }
    }

    private Livro montar(ResultSet rs) throws SQLException {
        int ano = rs.getInt("ano_publicacao");
        Integer anoPublicacao = rs.wasNull() ? null : ano;
        return new Livro(rs.getInt("id_livro"), rs.getString("titulo"), rs.getString("autor"),
                rs.getString("genero"), anoPublicacao, rs.getInt("quantidade_exemplares"));
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
