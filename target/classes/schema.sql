-- Remove tabelas existentes para garantir um estado limpo a cada reinicialização
DROP TABLE IF EXISTS presencas;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS agenda_apresentacoes;
DROP TABLE IF EXISTS corista_tipos_voz; -- Garante que a tabela nova seja removida
DROP TABLE IF EXISTS coristas;
DROP TABLE IF EXISTS musicos;

-- Tabela para autenticação
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Tabela de Coristas
CREATE TABLE coristas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo_voz VARCHAR(100), -- Coluna única para tipo de voz
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Musicistas
CREATE TABLE musicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    instrumento VARCHAR(100),
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela da Agenda
CREATE TABLE agenda_apresentacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    local VARCHAR(255),
    descricao TEXT
);

-- Tabela de Presenças
CREATE TABLE presencas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_participante INT NOT NULL,
    tipo_participante VARCHAR(20) NOT NULL,
    id_agenda INT NOT NULL,
    presente BOOLEAN NOT NULL,
    -- Adicionada a cláusula ON DELETE CASCADE
    FOREIGN KEY (id_agenda) REFERENCES agenda_apresentacoes(id) ON DELETE CASCADE,
    UNIQUE (id_participante, tipo_participante, id_agenda)
);