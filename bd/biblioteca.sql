create database biblioteca;
use biblioteca;

create table rol(
idRol int primary key,
nombreRol varchar(50)
);

create table usuario(
idusuario varchar(20) primary key,
nombres varchar(100),
apellidos varchar(150),
fechaNacimiento date,
email varchar(200) unique null,
password varchar(500) null,
fechaRegistro datetime,
estado bool,
idRol int,
CONSTRAINT FK_rol FOREIGN KEY (idRol) REFERENCES rol(idRol) on delete restrict
);


create table categoria(
idCategoria int primary key auto_increment,
nombre varchar(50)
);


create table libro(
idLibro varchar(13) primary key,
titulo varchar(150),
descripcion varchar(500),
estado bool,
cantidad int,
idCategoria int,
CONSTRAINT FK_categoria FOREIGN KEY (idCategoria) REFERENCES categoria(idCategoria) on delete restrict
);


create table estadoEntrega(
idEstado int primary key auto_increment,
descripcion varchar(20)
);


create table prestamo
(
idPrestamo int primary key auto_increment,
fechaPrestamo datetime,	
fechaVencimiento datetime,	
fechaEntrega datetime,	
idEstado int,
idUsuario varchar(20),	
idLibro varchar(13),
CONSTRAINT FK_estado FOREIGN KEY (idEstado) REFERENCES estadoEntrega(idEstado) on delete restrict,
CONSTRAINT FK_usuario FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) on delete restrict,
CONSTRAINT FK_libro FOREIGN KEY (idLibro) REFERENCES libro(idLibro) on delete restrict
);



DELIMITER $$
CREATE PROCEDURE sp_verificarDeudasUsuario(
IN i_idUsuario varchar(20)
)
BEGIN
select * from prestamo where idUsuario = i_idUsuario and (idEstado != 2 and idEstado != 4);
END$$


DELIMITER $$
CREATE PROCEDURE sp_listadoUsuarios()
begin
select u.idusuario, u.nombres, u.apellidos, u.fechaNacimiento, u.email, u.fechaRegistro, u.estado, u.idRol,
r.idRol, r.nombreRol 
from usuario u inner join rol r
on u.idRol = r.idRol;
END$$


DELIMITER $$
CREATE PROCEDURE sp_login(
IN i_email varchar(200),
IN i_password varchar(500)
)
BEGIN
   select * from usuario u inner join rol r
   on u.idRol = r.idRol
   where email = i_email and password = i_password;
END$$


DELIMITER $$
CREATE PROCEDURE sp_buscarUsuario(IN i_idusuario varchar(20))
begin
select u.idusuario, u.nombres, u.apellidos, u.fechaNacimiento, u.email, u.fechaRegistro, u.estado, u.idRol
from usuario u
where idusuario = i_idusuario;
END$$


DELIMITER $$
CREATE PROCEDURE sp_actualizarUsuario(
IN i_nombres varchar(100),
IN i_apellidos varchar(150),
IN i_fechaNacimiento date,
IN i_email varchar(200),
IN i_idUsuario varchar(20)
)
begin
UPDATE usuario SET 
nombres = i_nombres,
apellidos = i_apellidos,
fechaNacimiento = i_fechaNacimiento,
email = i_email
where idUsuario = i_idUsuario;
END$$


DELIMITER $$
CREATE  PROCEDURE sp_actualizarCantidadLibro(
IN i_idLibro varchar(13),
IN i_cantidad int
)
begin
set @cantidad = (select cantidad FROM libro);
update libro set cantidad = (@cantidad + i_cantidad) where idLibro =  i_idLibro;
END$$


insert into rol values
(1, "Encargado"),
(2,"Lector");

/*contra: admin1234*/
insert into usuario values
('71296307','Cesar','Aguilar Zambrano','2001-07-13',
'admin@gmail.com','ac9689e2272427085e35b9d3e3e8bed88cb3434828b43b86fc0596cad4c6e270', localtimestamp(),true,1);

insert into estadoEntrega values
(null,"Pendiente"),
(null,"Entregado"),
(null,"No entregado"),
(null,"Entregado tarde");
