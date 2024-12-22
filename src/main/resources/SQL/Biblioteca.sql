CREATE DATABASE IF NOT EXISTS biblioteca_db;
-- USE biblioteca; -- Elimino el USE perque ja esta especificat en el ConnexioDB.java en la part de String url = "jdbc:mysql://localhost:3306/biblioteca";

-- Taula llibres
CREATE TABLE IF NOT EXISTS llibres (
    id INT(11) PRIMARY KEY AUTO_INCREMENT,
    titol VARCHAR(200) NOT NULL,
    any_publicacio INT(11) NOT NULL
);

-- Taula autors
CREATE TABLE IF NOT EXISTS autors (
    id INT(11) PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    nacionalitat VARCHAR(100) NOT NULL
);

-- Taula prestecs
CREATE TABLE IF NOT EXISTS prestecs (
    id INT(11) PRIMARY KEY AUTO_INCREMENT,
    llibre_id INT(11),
    usuari VARCHAR(100) NOT NULL,
    data_prestec DATE NOT NULL,
    data_devolucio DATE NOT NULL,
    FOREIGN KEY (llibre_id) REFERENCES llibres(id) ON DELETE CASCADE
);

-- Taula intermitja per relacio molts-a-molts entre llibres i autors
CREATE TABLE IF NOT EXISTS llibres_autors (
    llibre_id INT(11),
    autor_id INT(11),
    PRIMARY KEY (llibre_id, autor_id),
    FOREIGN KEY (llibre_id) REFERENCES llibres(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES autors(id) ON DELETE CASCADE
);


-- REGISTRES DE PROVA PER PODER EXECUTAR ELS EXERCICIS

-- Inserir llibres de prova
INSERT INTO llibres (titol, any_publicacio) VALUES
('El senyor dels anells', 1954),
('1984', 1949),
('Cien años de soledad', 1967),
('El petit príncep', 1943),
('Harry Potter i la pedra filosofal', 1997);

-- Inserir autors de prova
INSERT INTO autors (nom, nacionalitat) VALUES
('J.R.R. Tolkien', 'Britànic'),
('George Orwell', 'Britànic'),
('Gabriel García Márquez', 'Colombià'),
('Antoine de Saint-Exupéry', 'Francès'),
('J.K. Rowling', 'Britànica');

-- Relacionar llibres amb autors (taula intermitja)
INSERT INTO llibres_autors (llibre_id, autor_id) VALUES
(1, 1), -- El senyor dels anells - Tolkien
(2, 2), -- 1984 - Orwell
(3, 3), -- Cien años de soledad - García Márquez
(4, 4), -- El petit príncep - Saint-Exupéry
(5, 5); -- Harry Potter - Rowling

-- Inserir préstecs de prova
INSERT INTO prestecs (llibre_id, usuari, data_prestec, data_devolucio) VALUES
(1, 'Usuari1', '2023-12-01', '2023-12-15'),
(2, 'Usuari2', '2023-12-05', '2023-12-20'),
(3, 'Usuari3', '2023-12-10', '2023-12-25'),
(4, 'Usuari4', '2023-12-15', '2023-12-30'),
(5, 'Usuari5', '2023-12-20', '2024-01-05');
