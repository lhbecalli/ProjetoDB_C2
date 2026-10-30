package model;

public class Aluno {
    private int idAluno;
    private String nome;
    private String matricula;
    private String turma;
    private String email;

    public Aluno() {
    }

    public Aluno(int idAluno, String nome, String matricula, String turma, String email) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.matricula = matricula;
        this.turma = turma;
        this.email = email;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "Aluno [id=" + idAluno + ", nome=" + nome + ", matricula=" + matricula
                + ", turma=" + turma + ", email=" + email + "]";
    }
}
