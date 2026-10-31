# docs/

## Diagrama relacional

`diagrama_relacional.mmd` tem o diagrama no formato Mermaid (`erDiagram`),
descrevendo as 3 tabelas (`tb_alunos`, `tb_livros`, `tb_emprestimos`), seus
atributos, chaves primarias/estrangeiras e a cardinalidade 1:N dos dois
relacionamentos.

O edital exige o diagrama entregue em **pdf, png ou jpeg** — o `.mmd` e o
"codigo-fonte" do diagrama, nao o formato de entrega final. Antes de
entregar o trabalho, renderize o arquivo em uma imagem, por exemplo:

- colando o conteudo em https://mermaid.live e exportando como PNG/PDF; ou
- usando a extensao "Markdown Preview Mermaid Support" do VSCode e
  exportando a partir da previa; ou
- com o Mermaid CLI: `npm install -g @mermaid-js/mermaid-cli` e depois
  `mmdc -i diagrama_relacional.mmd -o diagrama_relacional.png`. Em ambientes
  com Node/npm ja instalados, `npx -y @mermaid-js/mermaid-cli -i diagrama_relacional.mmd -o diagrama_relacional.png`
  tambem funciona, mas pode ser bloqueado por politicas de sandbox/permissao
  que proibem baixar e executar pacotes externos automaticamente (foi o caso
  ao tentar gerar essa imagem nesta sessao) — rode esse comando manualmente
  no seu terminal se isso acontecer.

Salve o resultado renderizado nesta mesma pasta (`docs/diagrama_relacional.png`
ou `.pdf`) antes da entrega final.
