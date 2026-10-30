package model;

import java.time.LocalDate;

public class Emprestimo {
    private int idEmprestimo;
    private Aluno aluno;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;

    public Emprestimo() {
    }

    public Emprestimo(int idEmprestimo, Aluno aluno, Livro livro, LocalDate dataEmprestimo,
                      LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao) {
        this.idEmprestimo = idEmprestimo;
        this.aluno = aluno;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String toString() {
        return "Emprestimo [id=" + idEmprestimo + ", aluno=" + aluno + ", livro=" + livro
                + ", emprestimo=" + dataEmprestimo + ", prevista=" + dataPrevistaDevolucao
                + ", devolucao=" + dataDevolucao + "]";
    }
}
