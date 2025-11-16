-- Inserir um usuário padrão para login
INSERT INTO usuario (username, password) VALUES ('admin', 'admin');

-- Inserir alguns coristas de exemplo (VERSÃO ANTIGA E ESTÁVEL)
INSERT INTO coristas (nome, tipo_voz, ativo) VALUES
('Ana Silva', 'Soprano', true),
('Bruno Costa', 'Tenor', true),
('Carla Dias', 'Contralto', false),
('Daniel Faria', 'Baixo', true);

-- Inserir alguns músicos de exemplo
INSERT INTO musicos (nome, instrumento, ativo) VALUES
('Fernanda Lima', 'Piano', true),
('Gustavo Melo', 'Violino', true);

-- Inserir alguns eventos na agenda
INSERT INTO agenda_apresentacoes (data, local, descricao) VALUES
('2025-11-20', 'Teatro Municipal', 'Concerto de Fim de Ano'),
('2025-12-05', 'Igreja da Sé', 'Apresentação de Natal');