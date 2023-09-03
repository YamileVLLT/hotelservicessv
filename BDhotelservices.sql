create database HotelServicesSV;

go

use HotelServicesSV;

create table Rol(
Id int not null identity(1,1),
Nombre nvarchar(30) not null,
primary key(Id)
);
go

create table Administrador(
Id int not null identity(1,1),
RolId int not null,
Nombre nvarchar(30) not null,
Apellido nvarchar(30) not null,
[Login] nvarchar(25) not null,
[Password] nvarchar not null,
Estatus tinyint not null,
FechaRegistro datetime not null,
primary key(Id),
foreign key(RolId) references Rol(Id)
);
go

create table Hotel(
Id int not null identity (1,1),
Nombre nvarchar(100) not null,
Imagen nvarchar(max) not null,
Direccion nvarchar(500) not null,
Descripcion nvarchar(900) not null,
Telefono nvarchar(50) not null,
Departamento nvarchar(100) not null,
Entrada nvarchar(50) not null,
Horario nvarchar(200) not null,
primary key(Id)
);
go

create table Servicio(
Id int not null identity (1,1), 
IdHotel int not null,
Servicios nvarchar(900) not null,
Estado nvarchar(50) not null,
primary key(id)
);
go

insert into Rol(Nombre) values('Administrador');

--password: admin2023 
insert into Administrador(RolId, Nombre, Apellido, [Login], [Password], Estatus, FechaRegistro) 
values(1, 'Andrea', 'Moran', 'am', '81ce825ec1ace3ee7cf7e92df2cc9905', 1, SYSDATETIME());


-- CREACION DEL LOGIN
CREATE LOGIN [hotel]
WITH PASSWORD = '#Modulo16',
DEFAULT_DATABASE = HotelServicesSV,
CHECK_POLICY = OFF,
CHECK_EXPIRATION = OFF;

-- ASIGNACIÓN DE PERMISOS
USE HotelServicesSV;
CREATE USER [hotel] FOR LOGIN [hotel] WITH DEFAULT_SCHEMA = dbo;
ALTER ROLE db_owner ADD MEMBER [hotel];