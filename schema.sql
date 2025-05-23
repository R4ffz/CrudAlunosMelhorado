-- schema.sql

-- Cria o banco (caso não exista) e posiciona no banco
CREATE DATABASE IF NOT EXISTS cruddb;
USE cruddb;

-- Tabela de Cursos
CREATE TABLE IF NOT EXISTS cursos (
  id   BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  sigla VARCHAR(10) NOT NULL UNIQUE,
  area VARCHAR(20) NOT NULL
);

-- Tabela de Alunos
CREATE TABLE IF NOT EXISTS alunos (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  nome        VARCHAR(100)    NOT NULL,
  email       VARCHAR(100)    NOT NULL,
  idade       INT             NOT NULL,
  curso       VARCHAR(10)     NOT NULL,
  sexo        VARCHAR(10)     NOT NULL,
  maioridade  TINYINT(1)      NOT NULL,
  CONSTRAINT  fk_aluno_curso
    FOREIGN KEY (curso)
    REFERENCES cursos(sigla)
);