-- Banco da Biblioteca Escolar (PostgreSQL)
-- Cria as tabelas conforme o CONTRATO_BANCO.md.
-- Pode rodar de novo: apaga as tabelas antes de criar.

DROP TABLE IF EXISTS tb_emprestimos;
DROP TABLE IF EXISTS tb_livros;
DROP TABLE IF EXISTS tb_alunos;

CREATE TABLE tb_alunos (
    id_aluno  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    matricula VARCHAR(20)  NOT NULL UNIQUE,
    turma     VARCHAR(20)  NOT NULL,
    email     VARCHAR(100)
);

CREATE TABLE tb_livros (
    id_livro              INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo                VARCHAR(150) NOT NULL,
    autor                 VARCHAR(100) NOT NULL,
    genero                VARCHAR(50),
    ano_publicacao        INTEGER,
    quantidade_exemplares INTEGER NOT NULL CHECK (quantidade_exemplares >= 0)
);

-- Sem ON DELETE CASCADE: o programa apaga os emprestimos antes, se o usuario confirmar.
CREATE TABLE tb_emprestimos (
    id_emprestimo           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_aluno                INTEGER NOT NULL REFERENCES tb_alunos (id_aluno),
    id_livro                INTEGER NOT NULL REFERENCES tb_livros (id_livro),
    data_emprestimo         DATE NOT NULL,
    data_prevista_devolucao DATE NOT NULL,
    data_devolucao          DATE
);
