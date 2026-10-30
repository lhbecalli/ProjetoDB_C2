# Banco da Biblioteca Escolar

Esquema das tabelas usado pelo script SQL, pelo model e pelos controllers.

Banco: PostgreSQL. Tabelas e colunas em minúsculo e sem acento.
Toda tabela começa com o prefixo `tb_` (ex.: `tb_livros`).

## tb_alunos
| coluna | tipo | regras |
|---|---|---|
| id_aluno | INTEGER | PK, GENERATED ALWAYS AS IDENTITY |
| nome | VARCHAR(100) | NOT NULL |
| matricula | VARCHAR(20) | NOT NULL, UNIQUE |
| turma | VARCHAR(20) | NOT NULL |
| email | VARCHAR(100) | pode ficar vazio |

## tb_livros
| coluna | tipo | regras |
|---|---|---|
| id_livro | INTEGER | PK, GENERATED ALWAYS AS IDENTITY |
| titulo | VARCHAR(150) | NOT NULL |
| autor | VARCHAR(100) | NOT NULL |
| genero | VARCHAR(50) | pode ficar vazio |
| ano_publicacao | INTEGER | pode ficar vazio |
| quantidade_exemplares | INTEGER | NOT NULL, CHECK (>= 0) |

## tb_emprestimos
| coluna | tipo | regras |
|---|---|---|
| id_emprestimo | INTEGER | PK, GENERATED ALWAYS AS IDENTITY |
| id_aluno | INTEGER | NOT NULL, FK para tb_alunos(id_aluno) |
| id_livro | INTEGER | NOT NULL, FK para tb_livros(id_livro) |
| data_emprestimo | DATE | NOT NULL |
| data_prevista_devolucao | DATE | NOT NULL |
| data_devolucao | DATE | NULL até o livro ser devolvido |

## Relacionamentos
- um aluno tem vários empréstimos (1 para N)
- um livro aparece em vários empréstimos (1 para N)
- sem ON DELETE CASCADE. Na hora de remover, o programa olha se tem
  empréstimo ligado e pergunta se o usuário quer apagar eles primeiro.

## Classes Java
| classe | atributos |
|---|---|
| Aluno | idAluno, nome, matricula, turma, email |
| Livro | idLivro, titulo, autor, genero, anoPublicacao, quantidadeExemplares |
| Emprestimo | idEmprestimo, aluno (Aluno), livro (Livro), dataEmprestimo, dataPrevistaDevolucao, dataDevolucao (LocalDate) |

## Relatórios
- com GROUP BY: total de empréstimos por aluno (ou por livro)
- com JOIN: empréstimos mostrando o nome do aluno e o título do livro, marcando os atrasados

## Lembrete para os inserts
Como a PK é identity, o INSERT não leva o id. Se precisar do id gerado,
usar `RETURNING id_xxx` no final.
