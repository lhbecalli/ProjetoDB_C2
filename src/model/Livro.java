package model;

public class Livro {
    private int idLivro;
    private String titulo;
    private String autor;
    private String genero;
    private Integer anoPublicacao;
    private int quantidadeExemplares;

    public Livro() {
    }

    public Livro(int idLivro, String titulo, String autor, String genero,
                 Integer anoPublicacao, int quantidadeExemplares) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeExemplares = quantidadeExemplares;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public void setQuantidadeExemplares(int quantidadeExemplares) {
        this.quantidadeExemplares = quantidadeExemplares;
    }

    public String toString() {
        return "Livro [id=" + idLivro + ", titulo=" + titulo + ", autor=" + autor
                + ", genero=" + genero + ", ano=" + anoPublicacao
                + ", exemplares=" + quantidadeExemplares + "]";
    }
}
