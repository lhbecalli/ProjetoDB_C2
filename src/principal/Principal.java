package principal;

import conexion.Conexao;
import reports.Relatorios;
import tela.Entrada;
import tela.TelaAluno;
import tela.TelaEmprestimo;
import tela.TelaLivro;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Principal {
    private static final String SISTEMA = "SISTEMA DE BIBLIOTECA ESCOLAR";
    // completar com os nomes de todos os integrantes do grupo
    private static final String[] GRUPO = {"LUIZ", "ATILLA"};
    private static final int LARGURA = 44;

    private static Entrada entrada;
    private static TelaAluno telaAluno;
    private static TelaLivro telaLivro;
    private static TelaEmprestimo telaEmprestimo;
    private static Relatorios relatorios;

    public static void main(String[] args) {
        entrada = new Entrada(new Scanner(System.in));
        telaAluno = new TelaAluno(entrada);
        telaLivro = new TelaLivro(entrada);
        telaEmprestimo = new TelaEmprestimo(entrada);
        relatorios = new Relatorios();

        splashScreen();

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = entrada.lerInteiro("Opcao");
            switch (opcao) {
                case 1:
                    menuRelatorios();
                    break;
                case 2:
                    menuEntidades("inserir");
                    break;
                case 3:
                    menuEntidades("remover");
                    break;
                case 4:
                    menuEntidades("atualizar");
                    break;
                case 0:
                    System.out.println("\nSaindo... ate logo!");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(LARGURA));
        System.out.println("MENU PRINCIPAL");
        System.out.println("=".repeat(LARGURA));
        System.out.println("1 - Relatorios");
        System.out.println("2 - Inserir registros");
        System.out.println("3 - Remover registros");
        System.out.println("4 - Atualizar registros");
        System.out.println("0 - Sair");
    }

    // Mostra as entidades e chama a tela da acao escolhida. Ao terminar, volta ao menu principal.
    private static void menuEntidades(String acao) {
        System.out.println("\n--- " + acao.toUpperCase() + " REGISTROS ---");
        System.out.println("1 - Alunos");
        System.out.println("2 - Livros");
        System.out.println("3 - Emprestimos");
        System.out.println("0 - Voltar");
        int opcao = entrada.lerInteiro("Opcao");

        switch (opcao) {
            case 1:
                if (acao.equals("inserir")) {
                    telaAluno.inserir();
                } else if (acao.equals("remover")) {
                    telaAluno.remover();
                } else {
                    telaAluno.atualizar();
                }
                break;
            case 2:
                if (acao.equals("inserir")) {
                    telaLivro.inserir();
                } else if (acao.equals("remover")) {
                    telaLivro.remover();
                } else {
                    telaLivro.atualizar();
                }
                break;
            case 3:
                if (acao.equals("inserir")) {
                    telaEmprestimo.inserir();
                } else if (acao.equals("remover")) {
                    telaEmprestimo.remover();
                } else {
                    telaEmprestimo.atualizar();
                }
                break;
            case 0:
                break;
            default:
                System.out.println("Opcao invalida.");
        }
    }

    private static void menuRelatorios() {
        System.out.println("\n--- RELATORIOS ---");
        System.out.println("1 - Total de emprestimos por aluno");
        System.out.println("2 - Emprestimos com aluno e livro");
        System.out.println("0 - Voltar");
        int opcao = entrada.lerInteiro("Opcao");

        switch (opcao) {
            case 1:
                relatorios.totalEmprestimosPorAluno();
                break;
            case 2:
                relatorios.emprestimosComAlunoELivro();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcao invalida.");
        }
    }

    private static void splashScreen() {
        String borda = "#".repeat(LARGURA);
        System.out.println(borda);
        linha(centralizar(SISTEMA));
        linha("");
        linha("TOTAL DE REGISTROS EXISTENTES");
        linha("  1 - ALUNOS:      " + contar("tb_alunos"));
        linha("  2 - LIVROS:      " + contar("tb_livros"));
        linha("  3 - EMPRESTIMOS: " + contar("tb_emprestimos"));
        linha("");
        linha("CRIADO POR: " + GRUPO[0]);
        for (int i = 1; i < GRUPO.length; i++) {
            linha("            " + GRUPO[i]);
        }
        linha("");
        linha("DISCIPLINA: BANCO DE DADOS");
        linha("            2026/2");
        linha("PROFESSOR: HOWARD ROATTI");
        System.out.println(borda);
    }

    private static void linha(String texto) {
        int espacos = LARGURA - 4 - texto.length();
        System.out.println("# " + texto + " ".repeat(Math.max(espacos, 0)) + " #");
    }

    private static String centralizar(String texto) {
        int margem = Math.max((LARGURA - 4 - texto.length()) / 2, 0);
        return " ".repeat(margem) + texto;
    }

    // select count(1) total_{entidade} from {entidade}
    private static String contar(String tabela) {
        String sql = "select count(1) total_" + tabela + " from " + tabela;
        try (Connection conn = Conexao.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return String.valueOf(rs.getInt(1));
        } catch (SQLException | RuntimeException e) {
            return "indisponivel";
        }
    }
}
