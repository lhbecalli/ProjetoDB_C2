package tela;

import controller.ControllerLivro;
import model.Livro;

import java.sql.SQLException;
import java.util.List;

public class TelaLivro {
    private final ControllerLivro controller = new ControllerLivro();
    private final Entrada entrada;

    public TelaLivro(Entrada entrada) {
        this.entrada = entrada;
    }

    public void inserir() {
        do {
            System.out.println("\n=== INSERIR LIVRO ===");
            Livro livro = new Livro();
            livro.setTitulo(entrada.lerTexto("Titulo", true));
            livro.setAutor(entrada.lerTexto("Autor", true));
            livro.setGenero(entrada.lerTexto("Genero (opcional)", false));
            livro.setAnoPublicacao(entrada.lerInteiroOpcional("Ano de publicacao"));
            livro.setQuantidadeExemplares(entrada.lerInteiro("Quantidade de exemplares"));

            try {
                controller.inserir(livro);
                System.out.println("Livro inserido: " + livro);
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel inserir: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja inserir mais algum livro?"));
    }

    public void atualizar() {
        do {
            System.out.println("\n=== ATUALIZAR LIVRO ===");
            Livro livro = escolher();
            if (livro == null) {
                break;
            }

            System.out.println("Aperte Enter para manter o valor atual.");
            livro.setTitulo(entrada.editarTexto("Titulo", livro.getTitulo()));
            livro.setAutor(entrada.editarTexto("Autor", livro.getAutor()));
            livro.setGenero(entrada.editarTexto("Genero", livro.getGenero()));
            if (entrada.confirmar("Alterar o ano de publicacao (atual: " + livro.getAnoPublicacao() + ")?")) {
                livro.setAnoPublicacao(entrada.lerInteiroOpcional("Novo ano"));
            }
            if (entrada.confirmar("Alterar a quantidade de exemplares (atual: " + livro.getQuantidadeExemplares() + ")?")) {
                livro.setQuantidadeExemplares(entrada.lerInteiro("Nova quantidade"));
            }

            try {
                Livro atualizado = controller.atualizar(livro);
                System.out.println("Livro atualizado: " + atualizado);
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel atualizar: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja atualizar mais algum livro?"));
    }

    public void remover() {
        do {
            System.out.println("\n=== REMOVER LIVRO ===");
            Livro livro = escolher();
            if (livro == null) {
                break;
            }

            if (!entrada.confirmar("Remover \"" + livro.getTitulo() + "\"?")) {
                break;
            }

            try {
                int emprestimos = controller.contarEmprestimos(livro.getIdLivro());
                boolean apagarEmprestimos = false;
                if (emprestimos > 0) {
                    System.out.println("Esse livro tem " + emprestimos + " emprestimo(s) registrado(s).");
                    apagarEmprestimos = entrada.confirmar("Remover tambem esses emprestimos?");
                    if (!apagarEmprestimos) {
                        System.out.println("Livro nao removido.");
                        continue;
                    }
                }
                controller.remover(livro.getIdLivro(), apagarEmprestimos);
                System.out.println("Livro removido.");
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel remover: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja remover mais algum livro?"));
    }

    // Lista os livros e devolve o escolhido, ou null se nao houver/for invalido.
    private Livro escolher() {
        try {
            List<Livro> livros = controller.listar();
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro cadastrado.");
                return null;
            }
            for (Livro l : livros) {
                System.out.println(l.getIdLivro() + " - " + l.getTitulo() + " (" + l.getAutor() + ")");
            }
            int id = entrada.lerInteiro("Digite o id do livro");
            Livro livro = controller.buscar(id);
            if (livro == null) {
                System.out.println("Livro nao encontrado.");
            }
            return livro;
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco: " + e.getMessage());
            return null;
        }
    }
}
