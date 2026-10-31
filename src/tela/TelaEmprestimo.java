package tela;

import controller.ControllerAluno;
import controller.ControllerEmprestimo;
import controller.ControllerLivro;
import model.Aluno;
import model.Emprestimo;
import model.Livro;

import java.sql.SQLException;
import java.util.List;

public class TelaEmprestimo {
    private final ControllerEmprestimo controller = new ControllerEmprestimo();
    private final ControllerAluno controllerAluno = new ControllerAluno();
    private final ControllerLivro controllerLivro = new ControllerLivro();
    private final Entrada entrada;

    public TelaEmprestimo(Entrada entrada) {
        this.entrada = entrada;
    }

    public void inserir() {
        do {
            System.out.println("\n=== INSERIR EMPRESTIMO ===");
            Aluno aluno = escolherAluno();
            if (aluno == null) {
                break;
            }
            Livro livro = escolherLivro();
            if (livro == null) {
                break;
            }

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setAluno(aluno);
            emprestimo.setLivro(livro);
            emprestimo.setDataEmprestimo(entrada.lerData("Data do emprestimo"));
            emprestimo.setDataPrevistaDevolucao(entrada.lerData("Data prevista de devolucao"));
            emprestimo.setDataDevolucao(null);

            try {
                controller.inserir(emprestimo);
                System.out.println("Emprestimo inserido: " + emprestimo);
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel inserir: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja inserir mais algum emprestimo?"));
    }

    public void atualizar() {
        do {
            System.out.println("\n=== ATUALIZAR EMPRESTIMO ===");
            Emprestimo emprestimo = escolher();
            if (emprestimo == null) {
                break;
            }

            System.out.println("Aperte Enter para manter o valor atual.");
            emprestimo.setDataEmprestimo(entrada.editarData("Data do emprestimo", emprestimo.getDataEmprestimo()));
            emprestimo.setDataPrevistaDevolucao(
                    entrada.editarData("Data prevista de devolucao", emprestimo.getDataPrevistaDevolucao()));

            if (emprestimo.getDataDevolucao() == null) {
                if (entrada.confirmar("Registrar a devolucao agora?")) {
                    emprestimo.setDataDevolucao(entrada.lerData("Data da devolucao"));
                }
            } else {
                if (entrada.confirmar("Alterar a data de devolucao (atual: " + emprestimo.getDataDevolucao() + ")?")) {
                    emprestimo.setDataDevolucao(entrada.lerData("Nova data de devolucao"));
                } else if (entrada.confirmar("Remover a devolucao registrada (voltar para em aberto)?")) {
                    emprestimo.setDataDevolucao(null);
                }
            }

            try {
                Emprestimo atualizado = controller.atualizar(emprestimo);
                System.out.println("Emprestimo atualizado: " + atualizado);
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel atualizar: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja atualizar mais algum emprestimo?"));
    }

    public void remover() {
        do {
            System.out.println("\n=== REMOVER EMPRESTIMO ===");
            Emprestimo emprestimo = escolher();
            if (emprestimo == null) {
                break;
            }

            if (!entrada.confirmar("Remover o emprestimo " + emprestimo.getIdEmprestimo() + "?")) {
                break;
            }

            try {
                controller.remover(emprestimo.getIdEmprestimo());
                System.out.println("Emprestimo removido.");
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel remover: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja remover mais algum emprestimo?"));
    }

    // Lista os emprestimos e devolve o escolhido, ou null se nao houver/for invalido.
    private Emprestimo escolher() {
        try {
            List<Emprestimo> emprestimos = controller.listar();
            if (emprestimos.isEmpty()) {
                System.out.println("Nenhum emprestimo cadastrado.");
                return null;
            }
            for (Emprestimo e : emprestimos) {
                System.out.println(e.getIdEmprestimo() + " - " + e.getAluno().getNome()
                        + " / " + e.getLivro().getTitulo()
                        + " (devolucao: " + (e.getDataDevolucao() == null ? "pendente" : e.getDataDevolucao()) + ")");
            }
            int id = entrada.lerInteiro("Digite o id do emprestimo");
            Emprestimo emprestimo = controller.buscar(id);
            if (emprestimo == null) {
                System.out.println("Emprestimo nao encontrado.");
            }
            return emprestimo;
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco: " + e.getMessage());
            return null;
        }
    }

    // Lista os alunos e devolve o escolhido, para usar na insercao do emprestimo.
    private Aluno escolherAluno() {
        try {
            List<Aluno> alunos = controllerAluno.listar();
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado. Cadastre um aluno antes de registrar um emprestimo.");
                return null;
            }
            for (Aluno a : alunos) {
                System.out.println(a.getIdAluno() + " - " + a.getNome() + " (" + a.getMatricula() + ")");
            }
            int id = entrada.lerInteiro("Digite o id do aluno");
            Aluno aluno = controllerAluno.buscar(id);
            if (aluno == null) {
                System.out.println("Aluno nao encontrado.");
            }
            return aluno;
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco: " + e.getMessage());
            return null;
        }
    }

    // Lista os livros e devolve o escolhido, para usar na insercao do emprestimo.
    private Livro escolherLivro() {
        try {
            List<Livro> livros = controllerLivro.listar();
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro cadastrado. Cadastre um livro antes de registrar um emprestimo.");
                return null;
            }
            for (Livro l : livros) {
                System.out.println(l.getIdLivro() + " - " + l.getTitulo() + " (" + l.getAutor() + ")");
            }
            int id = entrada.lerInteiro("Digite o id do livro");
            Livro livro = controllerLivro.buscar(id);
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
