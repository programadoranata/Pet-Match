# PetMatch (Java + Swing + MySQL + Ant)

Sistema desktop para gestão de pets, adotantes e adoções. O projeto segue uma estrutura MVC simples com DAO e conexão JDBC.

## Tecnologias
- Java (Swing)
- MySQL
- JDBC
- Ant (NetBeans)

## Estrutura do Projeto
- `src/model` — entidades (`Pet`, `Adotante`, `Adocao`)
- `src/dao` — acesso a dados (CRUD)
- `src/controller` — regras simples e integração com a UI
- `src/utils` — conexão JDBC (`ConnectionFactory`)
- `src/ui` — telas Swing (`MainFrame`)
- `src/main` — classe principal (`Main`)

## Banco de Dados
Crie o banco e as tabelas:

```sql
CREATE DATABASE PetMatch;
USE PetMatch;

CREATE TABLE pets (
  id_pet INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100),
  especie VARCHAR(50),
  idade INT,
  descricao TEXT,
  status VARCHAR(20)
);

CREATE TABLE adotantes (
  id_adotante INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100),
  telefone VARCHAR(20),
  tipo_preferido VARCHAR(50)
);

CREATE TABLE adocoes (
  id_adocao INT PRIMARY KEY AUTO_INCREMENT,
  id_pet INT,
  id_adotante INT,
  data_adocao DATE,
  FOREIGN KEY (id_pet) REFERENCES pets(id_pet),
  FOREIGN KEY (id_adotante) REFERENCES adotantes(id_adotante)
);
```

## Configuração do JDBC
Edite `src/utils/ConnectionFactory.java`:

- `URL`: `jdbc:mysql://localhost:3306/PetMatch`
- `USER`: seu usuário MySQL
- `PASS`: sua senha MySQL

Certifique-se de que o driver do MySQL está em `lib/` e adicionado ao projeto no NetBeans.

## Como Executar
No NetBeans:
1. Abra o projeto.
2. Verifique se o driver JDBC está configurado.
3. Execute a classe `src/main/Main.java`.

## Funcionalidades
- CRUD de Pets
- CRUD de Adotantes
- CRUD de Adoções
- Pesquisa por nome de Pet e Adotante na tela de Adoções
- Validação de existência de Pet/Adotante ao salvar adoção

## Observações
- A data de adoção usa seletor visual e é salva no formato `yyyy-MM-dd`.
- O formulário de Adoções possui busca para facilitar seleção de IDs.

## Próximos Passos (opcionais)
- Validações extras de formulário
- Relatórios e filtros
- Melhorias visuais na UI Swing
