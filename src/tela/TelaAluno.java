package tela;

import controller.ControllerAluno;
import model.Aluno;

import java.sql.SQLException;
import java.util.List;

public class TelaAluno {
    private final ControllerAluno controller = new ControllerAluno();
    private final Entrada entrada;

    public TelaAluno(Entrada entrada) {
        this.entrada = entrada;
    }

    public void inserir() {
        do {
            System.out.println("\n=== INSERIR ALUNO ===");
            Aluno aluno = new Aluno();
            aluno.setNome(entrada.lerTexto("Nome", true));
            aluno.setMatricula(entrada.lerTexto("Matricula", true));
            aluno.setTurma(entrada.lerTexto("Turma", true));
            aluno.setEmail(entrada.lerTexto("E-mail (opcional)", false));

            try {
                controller.inserir(aluno);
                System.out.println("Aluno inserido: " + aluno);
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel inserir: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja inserir mais algum aluno?"));
    }

    public void atualizar() {
        do {
            System.out.println("\n=== ATUALIZAR ALUNO ===");
            Aluno aluno = escolher();
            if (aluno == null) {
                break;
            }

            System.out.println("Aperte Enter para manter o valor atual.");
            aluno.setNome(entrada.editarTexto("Nome", aluno.getNome()));
            aluno.setMatricula(entrada.editarTexto("Matricula", aluno.getMatricula()));
            aluno.setTurma(entrada.editarTexto("Turma", aluno.getTurma()));
            aluno.setEmail(entrada.editarTexto("E-mail", aluno.getEmail()));

            try {
                Aluno atualizado = controller.atualizar(aluno);
                System.out.println("Aluno atualizado: " + atualizado);
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel atualizar: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja atualizar mais algum aluno?"));
    }

    public void remover() {
        do {
            System.out.println("\n=== REMOVER ALUNO ===");
            Aluno aluno = escolher();
            if (aluno == null) {
                break;
            }

            if (!entrada.confirmar("Remover " + aluno.getNome() + "?")) {
                break;
            }

            try {
                int emprestimos = controller.contarEmprestimos(aluno.getIdAluno());
                boolean apagarEmprestimos = false;
                if (emprestimos > 0) {
                    System.out.println("Esse aluno tem " + emprestimos + " emprestimo(s) registrado(s).");
                    apagarEmprestimos = entrada.confirmar("Remover tambem esses emprestimos?");
                    if (!apagarEmprestimos) {
                        System.out.println("Aluno nao removido.");
                        continue;
                    }
                }
                controller.remover(aluno.getIdAluno(), apagarEmprestimos);
                System.out.println("Aluno removido.");
            } catch (IllegalStateException | SQLException e) {
                System.out.println("Nao foi possivel remover: " + e.getMessage());
            }
        } while (entrada.confirmar("Deseja remover mais algum aluno?"));
    }

    // Lista os alunos e devolve o escolhido, ou null se nao houver/for invalido.
    private Aluno escolher() {
        try {
            List<Aluno> alunos = controller.listar();
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return null;
            }
            for (Aluno a : alunos) {
                System.out.println(a.getIdAluno() + " - " + a.getNome() + " (" + a.getMatricula() + ")");
            }
            int id = entrada.lerInteiro("Digite o id do aluno");
            Aluno aluno = controller.buscar(id);
            if (aluno == null) {
                System.out.println("Aluno nao encontrado.");
            }
            return aluno;
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco: " + e.getMessage());
            return null;
        }
    }
}
