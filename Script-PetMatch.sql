use PetMatch;

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


