# PROMPTS.md

Registro de uso de IA, conforme o edital (item 5 das observações).
Para cada uso, anotar: **prompt**, **fonte** (ferramenta) e **o que o grupo fez com a resposta**.

Fonte de todos os registros abaixo: Claude Code (modelo Claude Sonnet 5.5), usado pelo Luiz e Atilla.
Os prompts estão resumidos, nas palavras que usamos.

| Data | Quem | Prompt | O que fizemos com a resposta |
|---|---|---|---|
| 30/10/2026 | Luiz | Enviamos o PDF do edital e pedimos para ler e se contextualizar antes de dividir as tarefas com o Atilla. | Usamos o resumo dos requisitos e da pontuação para entender o que o trabalho pede. |
| 30/10/2026 | Luiz | "Vamos fazer da biblioteca escolar, e banco e linguagem quais opcoes temos?" | Recebemos a comparação entre bancos e linguagens. O grupo escolheu Java e PostgreSQL. |
| 30/10/2026 | Luiz | Criamos uma divisão de tarefas entre o Luiz e o Atilla, em duas branches. | Usamos a divisão e trocamos algumas partes entre nós. O Atilla ficou com script SQL, diagrama, conexão, menus, Emprestimo, relatórios e principal. O Luiz ficou com model, controllers de Aluno e Livro, interfaces e README. |
| 30/10/2026 | Luiz | Criamos o esquema das 3 tabelas (alunos, livros, emprestimos). | Revisamos e reescrevemos o texto do `CONTRATO_BANCO.md`. Usamos as colunas e tipos como base do model e do script. |
| 30/10/2026 | Luiz | Criamos o esqueleto de pastas, o `.gitignore` e o `PROMPTS.md`. | Usamos como estrutura inicial do repositório. |
| 30/10/2026 | Luiz | Criamos as classes do pacote `model` (Aluno, Livro, Emprestimo). | Conferimos se compilam, e o Luiz fez o commit na branch dele. |
| 30/10/2026 | Luiz | Criamos os controllers de Aluno e Livro, sem ORM e com o SQL concatenado. | Conferimos se compilam, e o Luiz fez o commit. Ainda falta testar com o banco. |
| 30/10/2026 | Luiz | Instalamos o Maven e o PostgreSQL. | O PostgreSQL já estava instalado, e o Maven não foi instalado. A decisão sobre o Maven continua em aberto. |