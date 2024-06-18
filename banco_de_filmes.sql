create database banco_de_filmes;

use banco_de_filmes;
	
SELECT * FROM filmes;
SELECT * FROM filmes_favoritos;

DELETE FROM filmes;
DELETE FROM filmes_favoritos;

DESCRIBE filmes;
DESCRIBE filmes_favoritos;

ALTER TABLE filmes_favoritos
ADD COLUMN usuario_id integer,
ADD CONSTRAINT fk_usuario_id
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);


ALTER TABLE filmes CHANGE adult para_maiores varchar(255);
ALTER TABLE filmes CHANGE backdrop_path poster varchar(255);
ALTER TABLE filmes CHANGE overview sinopse text;
ALTER TABLE filmes CHANGE release_date data_de_lancamento varchar(255);
ALTER TABLE filmes CHANGE title titulo varchar(255);
ALTER TABLE filmes CHANGE vote_average avaliacao varchar(255);
ALTER TABLE filmes CHANGE para_maiores classificacao_indicativa varchar(255);


CREATE TABLE filmes_favoritos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    classificacao_indicativa VARCHAR(255),
    poster VARCHAR(255),
    sinopse varchar(768) unique,
    data_de_lancamento VARCHAR(255),
    titulo VARCHAR(255),
    avaliacao DOUBLE
);

ALTER TABLE filmes ADD UNIQUE (sinopse(768));
SHOW INDEX FROM filmes WHERE Non_unique = 0 AND Column_name = 'titulo';
ALTER TABLE filmes_favoritos DROP INDEX sinopse;
SHOW INDEX FROM filmes;

SHOW INDEX FROM filmes_favoritos;

        
SET SQL_SAFE_UPDATES = 0;
SET SQL_SAFE_UPDATES = 1;

ALTER TABLE filmes AUTO_INCREMENT = 1;
ALTER TABLE filmes_favoritos AUTO_INCREMENT = 1;
ALTER TABLE usuarios AUTO_INCREMENT = 1;


create table usuarios (
id integer not null primary key auto_increment,
email varchar(255) not null unique,
senha varchar(255) not null
);

describe usuarios;
select * from usuarios;
alter table usuarios add COLUMN nome varchar(255);












