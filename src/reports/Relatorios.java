package reports;

import conexion.Conexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Consultas de relatorio: uma com agregacao (GROUP BY) e outra com juncao (JOIN).
public class Relatorios {
    private static final int LARGURA = 60;

    public void totalEmprestimosPorAluno() {
        String sql = "SELECT a.nome, COUNT(e.id_emprestimo) AS total "
                + "FROM tb_alunos a "
                + "LEFT JOIN tb_emprestimos e ON e.id_aluno = a.id_aluno "
                + "GROUP BY a.nome "
                + "ORDER BY total DESC, a.nome";

        System.out.println("\n--- TOTAL DE EMPRESTIMOS POR ALUNO ---");
        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            cabecalho("ALUNO", "TOTAL");
            boolean algumRegistro = false;
            while (rs.next()) {
                algumRegistro = true;
                linha(rs.getString("nome"), String.valueOf(rs.getInt("total")));
            }
            if (!algumRegistro) {
                System.out.println("Nenhum aluno cadastrado.");
            }
        } catch (SQLException e) {
            System.out.println("Nao foi possivel gerar o relatorio: " + e.getMessage());
        }
    }

    public void emprestimosComAlunoELivro() {
        String sql = "SELECT a.nome AS aluno, l.titulo AS livro, e.data_emprestimo, "
                + "e.data_prevista_devolucao, e.data_devolucao, "
                + "(e.data_devolucao IS NULL AND e.data_prevista_devolucao < CURRENT_DATE) AS atrasado "
                + "FROM tb_emprestimos e "
                + "JOIN tb_alunos a ON a.id_aluno = e.id_aluno "
                + "JOIN tb_livros l ON l.id_livro = e.id_livro "
                + "ORDER BY e.data_emprestimo DESC";

        System.out.println("\n--- EMPRESTIMOS (ALUNO E LIVRO) ---");
        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean algumRegistro = false;
            while (rs.next()) {
                algumRegistro = true;
                String situacao = rs.getBoolean("atrasado") ? "ATRASADO"
                        : (rs.getDate("data_devolucao") == null ? "em aberto" : "devolvido");
                System.out.println(rs.getString("aluno") + " - \"" + rs.getString("livro") + "\""
                        + " | emprestimo: " + rs.getDate("data_emprestimo")
                        + " | previsto: " + rs.getDate("data_prevista_devolucao")
                        + " | situacao: " + situacao);
            }
            if (!algumRegistro) {
                System.out.println("Nenhum emprestimo cadastrado.");
            }
        } catch (SQLException e) {
            System.out.println("Nao foi possivel gerar o relatorio: " + e.getMessage());
        }
    }

    private void cabecalho(String colunaEsquerda, String colunaDireita) {
        System.out.println("-".repeat(LARGURA));
        System.out.printf("%-40s %15s%n", colunaEsquerda, colunaDireita);
        System.out.println("-".repeat(LARGURA));
    }

    private void linha(String colunaEsquerda, String colunaDireita) {
        System.out.printf("%-40s %15s%n", colunaEsquerda, colunaDireita);
    }
}
