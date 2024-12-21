CREATE DATABASE IF NOT EXISTS biblioteca;
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
