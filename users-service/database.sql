DROP DATABASE IF EXISTS users_service;
CREATE DATABASE users_service CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE users_service;

CREATE TABLE users (
	id				CHAR(36) 		NOT NULL,
	username		VARCHAR(50) 	NOT NULL UNIQUE,
	email			VARCHAR(260)	NOT	NULL UNIQUE,
	name			CHAR(200)		NOT NULL,
	image			VARCHAR(400)	NOT NULL,
	birthdate		DATE 			NOT NULL,
	createdAt		TIMESTAMP		NOT NULL DEFAULT NOW(),

	PRIMARY KEY (id)
);