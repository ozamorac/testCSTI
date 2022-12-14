DROP TABLE IF EXISTS notacurso;
DROP TABLE IF EXISTS usuario;

CREATE TABLE notacurso  (
  id INT AUTO_INCREMENT PRIMARY KEY,
  IdCurso INT NOT NULL,
  IdUsuario INT NOT NULL,
  Ciclo INT NOT NULL,
  Nota1 Double NOT NULL,
  Nota2 Double NOT NULL,
  Nota3 Double NOT NULL,
  Nota4 Double NOT NULL
);

CREATE TABLE usuario  (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL
);