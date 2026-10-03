-- 1. Tabela USUARIO
CREATE TABLE IF NOT EXISTS USUARIO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);

-- 2. Tabela PROFESSOR (Herança de USUARIO)
CREATE TABLE IF NOT EXISTS PROFESSOR (
    id BIGINT PRIMARY KEY,
    instituicao VARCHAR(255),
    CONSTRAINT fk_professor_usuario FOREIGN KEY (id) REFERENCES USUARIO(id) ON DELETE CASCADE
);

-- 3. Tabela ALUNO (Herança de USUARIO)
CREATE TABLE IF NOT EXISTS ALUNO (
    id BIGINT PRIMARY KEY,
    pontos_gamificacao INT DEFAULT 0,
    streak_atual INT DEFAULT 0,
    ultima_revisao TIMESTAMP,
    CONSTRAINT fk_aluno_usuario FOREIGN KEY (id) REFERENCES USUARIO(id) ON DELETE CASCADE
);

-- 4. Tabela TURMA
CREATE TABLE IF NOT EXISTS TURMA (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo_acesso VARCHAR(50) NOT NULL UNIQUE,
    professor_id BIGINT,
    CONSTRAINT fk_turma_professor FOREIGN KEY (professor_id) REFERENCES PROFESSOR(id) ON DELETE SET NULL
);

-- 5. Tabela N:M entre ALUNO e TURMA (Matrícula)
CREATE TABLE IF NOT EXISTS ALUNO_TURMA (
    aluno_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    PRIMARY KEY (aluno_id, turma_id),
    CONSTRAINT fk_at_aluno FOREIGN KEY (aluno_id) REFERENCES ALUNO(id) ON DELETE CASCADE,
    CONSTRAINT fk_at_turma FOREIGN KEY (turma_id) REFERENCES TURMA(id) ON DELETE CASCADE
);

-- 6. Tabela DECK
CREATE TABLE IF NOT EXISTS DECK (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tema VARCHAR(255) NOT NULL,
    descricao VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    turma_id BIGINT,
    CONSTRAINT fk_deck_turma FOREIGN KEY (turma_id) REFERENCES TURMA(id) ON DELETE CASCADE
);

-- 7. Tabela FLASHCARD
CREATE TABLE IF NOT EXISTS FLASHCARD (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    frente TEXT NOT NULL,
    verso TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deck_id BIGINT,
    CONSTRAINT fk_flashcard_deck FOREIGN KEY (deck_id) REFERENCES DECK(id) ON DELETE CASCADE
);

-- 8. Tabela PROGRESSO_SRS
CREATE TABLE IF NOT EXISTS PROGRESSO_SRS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    flashcard_id BIGINT NOT NULL,
    fator_facilidade FLOAT DEFAULT 2.5,
    intervalo_dias INT DEFAULT 1,
    repeticoes_consecutivas INT DEFAULT 0,
    data_proxima_revisao TIMESTAMP,
    CONSTRAINT fk_srs_aluno FOREIGN KEY (aluno_id) REFERENCES ALUNO(id) ON DELETE CASCADE,
    CONSTRAINT fk_srs_flashcard FOREIGN KEY (flashcard_id) REFERENCES FLASHCARD(id) ON DELETE CASCADE
);

-- Inserindo Usuários
INSERT INTO USUARIO (id, nome, email, senha, cargo) VALUES (1, 'Professor Carlos', 'carlos@decoramais.com', '123456', 'PROFESSOR');
INSERT INTO USUARIO (id, nome, email, senha, cargo) VALUES (2, 'Aluno João', 'joao@decoramais.com', '123456', 'ALUNO');

-- Especializando Usuários
INSERT INTO PROFESSOR (id, instituicao) VALUES (1, 'Fatec');
INSERT INTO ALUNO (id, pontos_gamificacao, streak_atual, ultima_revisao) VALUES (2, 150, 3, CURRENT_TIMESTAMP);

-- Inserindo Turma do Professor
INSERT INTO TURMA (id, nome, codigo_acesso, professor_id) VALUES (1, 'Desenvolvimento Web', 'DEC123', 1);

-- Matriculando Aluno na Turma
INSERT INTO ALUNO_TURMA (aluno_id, turma_id) VALUES (2, 1);

-- Inserindo Deck na Turma
INSERT INTO DECK (id, tema, descricao, data_criacao, turma_id) VALUES (1, 'Conceitos de Spring Boot', 'Baralho de flashcards sobre JPA e Hibernate', CURRENT_TIMESTAMP, 1);

-- Inserindo Flashcards no Deck
INSERT INTO FLASHCARD (id, frente, verso, data_criacao, deck_id) VALUES (1, 'Para que serve a anotação @Entity?', 'Mapeia uma classe Java como uma tabela no banco de dados relacional.', CURRENT_TIMESTAMP, 1);
INSERT INTO FLASHCARD (id, frente, verso, data_criacao, deck_id) VALUES (2, 'Qual a função da propriedade defer-datasource-initialization?', 'Adia a execução do data.sql para rodar após o Hibernate criar o schema.', CURRENT_TIMESTAMP, 1);

-- Registrando Progresso SRS do Aluno
INSERT INTO PROGRESSO_SRS (id, aluno_id, flashcard_id, fator_facilidade, intervalo_dias, repeticoes_consecutivas, data_proxima_revisao) 
VALUES (1, 2, 1, 2.5, 3, 2, TIMESTAMPADD(DAY, 3, CURRENT_TIMESTAMP));