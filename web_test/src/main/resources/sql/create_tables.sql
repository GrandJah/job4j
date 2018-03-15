DROP TABLE IF EXISTS usermusic;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS musictypes;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
  id SERIAL PRIMARY KEY UNIQUE,
  name VARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS addresses (
  id SERIAL PRIMARY KEY UNIQUE,
  address VARCHAR(250) NOT NULL UNIQUE
);
CREATE TABLE  IF NOT EXISTS musictypes (
  id SERIAL PRIMARY KEY UNIQUE,
  type VARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY UNIQUE,
  login VARCHAR(30) NOT NULL UNIQUE,
  role INTEGER REFERENCES roles(id) NOT NULL DEFAULT 1,
  address INTEGER REFERENCES addresses(id) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS usermusic (
  id SERIAL PRIMARY KEY UNIQUE,
  user_id INTEGER REFERENCES users(id) NOT NULL,
  musictype INTEGER REFERENCES musictypes(id) NOT NULL
);
INSERT INTO roles (name) VALUES ('USER'),('MANDATOR'),('ADMIN');
INSERT INTO musictypes (type) VALUES ('RAP'),('ROCK'),('POP'),('JAZZ');
INSERT INTO addresses (address) VALUES ('address_login1');
INSERT INTO addresses (address) VALUES ('address_login2');
INSERT INTO addresses (address) VALUES ('address_login3');
INSERT INTO addresses (address) VALUES ('address_login4');
INSERT INTO addresses (address) VALUES ('address_login5');
INSERT INTO addresses (address) VALUES ('address_login6');
INSERT INTO users (login, address) VALUES ('login1', 1);
INSERT INTO users (login, address) VALUES ('login2', 2);
INSERT INTO users (login, address) VALUES ('login3', 3);
INSERT INTO users (login, address) VALUES ('login4', 4);
INSERT INTO users (login, address) VALUES ('login5', 5);
INSERT INTO users (login, address) VALUES ('login6', 6);
INSERT INTO usermusic (user_id, musictype) VALUES (1,2),(3,2),(1,3),(2,2),(3,1);