DROP DATABASE IF EXISTS auth_service;
CREATE DATABASE auth_service CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE auth_service;

/*
CREATE TABLE auth (
	id				CHAR(32) 		NOT NULL,
	username		VARCHAR(50) 	NOT NULL UNIQUE,
	password		CHAR(64)		NOT NULL,
	
	PRIMARY KEY (id)
);*/