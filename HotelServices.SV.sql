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
foreign key(RolId) references Roles(Id)
);
go

create table Hotel(
Id int not null identity (1,1),
Nombre nvarchar(100) not null,
Imagen nvarchar(max) not null,
Direccion nvarchar(200) not null,
Descripcion nvarchar(800) not null,
Telefono nvarchar(15) not null,
Departamento nvarchar(50) not null,
Entrada nvarchar(10) not null,
Horario nvarchar(100) not null,
primary key(Id)
);
go

create table Servicio(
Id int not null identity (1,1), 
IdHotel int not null,
Servicios nvarchar(100) not null,
Estado nvarchar(50) not null,
primary key(id)
);
go

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