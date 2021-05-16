DROP DATABASE IF EXISTS api_gateway;
CREATE DATABASE api_gateway CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE api_gateway;

CREATE TABLE auth (
	id				CHAR(32) 		NOT NULL,
	username		VARCHAR(50) 	NOT NULL UNIQUE,
	password		CHAR(64)		NOT NULL,
	
	PRIMARY KEY (id)
);