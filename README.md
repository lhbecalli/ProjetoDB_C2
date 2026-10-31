# Sistema de Biblioteca Escolar

Projeto da disciplina de Banco de Dados (prof. Howard Roatti). Aplicacao Java
com JDBC puro (sem ORM) sobre PostgreSQL, controlando Alunos, Livros e
Emprestimos.

## Pre-requisitos (Linux)

- JDK 11 ou superior (`sudo apt install default-jdk` ou equivalente).
- PostgreSQL acessivel (local ou remoto), com o banco ja criado.
- Opcional: Maven (`sudo apt install maven`), se preferir a Opcao B abaixo em
  vez de baixar o driver JDBC manualmente.
- Se nao for usar Maven: driver JDBC do PostgreSQL (`postgresql-<versao>.jar`).
  Baixe em https://jdbc.postgresql.org/download/ e salve na raiz do projeto
  como `postgresql.jar`.

## Configuracao do banco

1. Rode o script `sql/01_criar_tabelas.sql` no banco PostgreSQL:
   ```bash
   psql -h <host> -p <porta> -U <usuario> -d <banco> -f sql/01_criar_tabelas.sql
   ```
2. Copie `config.exemplo.properties` para `config.local.properties` na raiz
   do projeto e preencha `db.host`, `db.porta`, `db.nome`, `db.usuario` e
   `db.senha`. Esse arquivo fica fora do Git (veja `.gitignore`).

## Compilar e executar

### Opcao A: manual (javac/classpath)

Da raiz do projeto, com o driver JDBC salvo como `postgresql.jar`:

```bash
mkdir -p bin
javac -d bin -cp .:postgresql.jar $(find src -name "*.java")
java -cp bin:postgresql.jar:. principal.Principal
```

O `.` no classpath e necessario para o programa encontrar o
`config.local.properties` na raiz do projeto.

### Opcao B: usando Maven

O `pom.xml` na raiz aponta `sourceDirectory` para a pasta `src` existente
(sem precisar migrar para `src/main/java`) e resolve o driver JDBC do
PostgreSQL como dependencia automaticamente.

```bash
mvn compile
mvn exec:java -Dexec.mainClass="principal.Principal"
```

Ou para gerar um jar executavel:

```bash
mvn package
java -cp target/classes:$(find ~/.m2 -name "postgresql-*.jar" | head -1):. principal.Principal
```

Assim como na Opcao A, rode esses comandos a partir da raiz do projeto para
que o `config.local.properties` seja encontrado.

## Estrutura de pastas

- `src/model` — classes das entidades (`Aluno`, `Livro`, `Emprestimo`).
- `src/controller` — acesso ao banco via JDBC, com SQL concatenado (sem ORM).
- `src/tela` — interacao com o usuario no console (menus, leitura de dados).
- `src/conexion` — abertura da conexao JDBC com o PostgreSQL.
- `src/reports` — consultas de relatorio (agregacao e juncao de tabelas).
- `src/principal` — classe `Principal`, ponto de entrada do programa.
- `sql/` — script de criacao das tabelas (`01_criar_tabelas.sql`).
- `docs/` — diagrama relacional e documentacao auxiliar.

## Uso

Ao iniciar, o programa mostra uma splash screen com o total de registros de
cada tabela e, em seguida, o menu principal com as opcoes de relatorios,
inserir, remover, atualizar e sair. Os submenus pedem para escolher a
entidade (Aluno, Livro ou Emprestimo) antes de cada operacao, e voltam a
perguntar se o usuario deseja repetir a acao ao final de cada operacao.
