DROP DATABASE IF EXISTS pr04;
CREATE DATABASE pr04;
USE pr04;

CREATE TABLE generos (
  genero varchar(50) NOT NULL,
  PRIMARY KEY (genero)
);

CREATE TABLE fotogramas (
  idFotograma int(10) NOT NULL,
  archivo varchar(50) NOT NULL,
  titPelicula varchar(250) NOT NULL,
  anyoEstreno int(10) NOT NULL,
  director varchar(250) NOT NULL,
  genero varchar(50) NOT NULL,
  PRIMARY KEY (idFotograma),
  FOREIGN KEY (genero) REFERENCES generos(genero)
);

CREATE TABLE usuarios (
  login varchar(12) NOT NULL,
  clave varchar(12) NOT NULL,
  PRIMARY KEY (login)
);

CREATE TABLE concurso (
  login varchar(12) NOT NULL,
  numGlobalAciertos int(11) DEFAULT 0,
  numGlobalFotOfrecidos int(11) DEFAULT 0,
  porAciertosGlobal int(11) DEFAULT 0,
  PRIMARY KEY (login),
  FOREIGN KEY (login) REFERENCES usuarios(login)
);

CREATE TABLE ranking (
  login varchar(12) NOT NULL,
  puntos int(11) DEFAULT NULL,
  PRIMARY KEY (login),
  FOREIGN KEY (login) REFERENCES usuarios(login)
);

CREATE TABLE fotacertados (
  login varchar(12) NOT NULL,
  idFotograma int(11) NOT NULL,
  acertado boolean NOT NULL default false,
  PRIMARY KEY (login, idFotograma)
);

INSERT INTO generos VALUES 
('Accion'),
('Comedia'),
('Drama'),
('Intriga'),
('Policiaca'),
('Thriller'),
('Ciencia Ficción'),
('Bélico');

INSERT INTO fotogramas VALUES 
(0,'chacal.jpg','Chacal',1973,'Fred Zinnemann','Policiaca'),
(1,'ciudadano.jpg','Un ciudadano ejemplar',2009,'F. Gary Gray','Intriga'),
(2,'diablo.jpg','Diablo',2003,'F. Gary Gray','Accion'),
(3,'romperstomper.jpg','Romper stomper',1992,'Geoffrey Wright','Accion'),
(4,'invicto3.jpg','Invicto 3',2010,'Isaac Florentine','Accion'),
(5,'scout.jpg','El ultimo boy scout',1991,'Tony Scott','Accion'),
(6,'tirador.jpg','El tirador',2007,'Antoine Fuqua','Thriller'),
(7,'vecinos.jpg','Vecinos invasores',2006,'Tim Johnson','Comedia'),
(8,'england.jpg','This is England',2006,'Shane Meadows','Drama'),
(9,'gladiator.jpg','Gladiator',2000,'Ridley Scott','Accion'),
(10,'alien1.jpg','Alien, el octavo pasajero',1979,'Ridley Scott','Ciencia Ficción'),
(11,'alien_regreso.jpg','Aliens: El regreso',1986,'James Cameron','Ciencia Ficción'),
(12,'alien3.jpg','Alien 3',1992,'David Fincher','Ciencia Ficción'),
(13,'alien_resurreccion.jpg','Alien: Resurreccion',1997,'Jean-Pierre Jeunet','Ciencia Ficción'),
(14,'prometheus.jpg','Prometheus',2012,'Ridley Scott','Ciencia Ficción'),
(15,'alien_covenant.jpg','Alien: Covenant',2017,'Ridley Scott','Ciencia Ficción'),
(16,'2001.jpg','2001: Una odisea del espacio',1968,'Stanley Kubrick','Ciencia Ficción'),
(17,'2010.jpg','2010: Odisea Dos',1984,'Peter Hyams','Ciencia Ficción'),
(18,'1898losultimos.jpg','1898. Los ultimos de Filipinas',2016,'Salvador Calvo','Bélico'),
(19,'rs_extinction.jpg','Resident Evil: Extinction',2007,'Russell Mulcahy','Accion'),
(20,'yorobot.jpg','Yo, robot',2004,'Alex Proyas','Ciencia Ficción'),
(21,'aterriza_puedas.jpg','Aterriza como puedas',1980,'Jim Abrahams','Comedia'),
(22,'wall_e.jpg','WALL-E',2008,'Andrew Stanton','Ciencia Ficción');

INSERT INTO usuarios VALUES 
('bub','bub'),
('frol','frol'),
('rox','rox'),
('juan','juan'),
('german','german'),
('yo','yo'),
('clara','clara'),
('admin','admin'),
('antonio','antonio'),
('pablo','pablo'),
('usuario','usuario');

INSERT INTO ranking VALUES 
('juan',2),
('frol',5),
('usuario',1),
('german',9),
('bub',8),
('yo',15),
('pablo',20),
('rox',8),
('clara',7),
('antonio',7),
('admin',25);

INSERT INTO fotacertados (login,idFotograma,acertado) VALUES
('bub',0,false),
('bub',1,false),
('bub',2,true),
('bub',3,false),
('bub',4,false),
('bub',5,true),
('bub',6,false),
('bub',7,false),
('bub',8,true),
('bub',9,false)
;
