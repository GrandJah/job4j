--Обнуление таблиц
DROP TABLE IF EXISTS user_location;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS use_role;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

-- создание таблиц
CREATE TABLE IF NOT EXISTS countries (
  id SERIAL PRIMARY KEY UNIQUE,
  country VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS cities (
  id SERIAL PRIMARY KEY UNIQUE,
  city VARCHAR(30) NOT NULL,
  country INTEGER REFERENCES countries (id) NOT NULL
);
CREATE TABLE IF NOT EXISTS roles (
  id SERIAL PRIMARY KEY UNIQUE,
  name VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY UNIQUE,
  login VARCHAR(30) UNIQUE,
  name VARCHAR(50),
  email VARCHAR(30),
  created TIMESTAMP
);
CREATE TABLE IF NOT EXISTS use_role (
  user_id INTEGER REFERENCES users(id) PRIMARY KEY UNIQUE NOT NULL,
  role_id INTEGER REFERENCES roles(id) NOT NULL
);
CREATE TABLE IF NOT EXISTS user_location (
  user_id INTEGER REFERENCES users(id) PRIMARY KEY UNIQUE NOT NULL,
  city_id INTEGER REFERENCES cities(id) NOT NULL
);
--Наполнение таблицы
INSERT INTO countries (country) VALUES
  ('-------'),
  ('Russia'),
  ('USA'),
  ('UK');
INSERT INTO cities (city, country) VALUES
  ('-------', 1),
  ('Moscow', 2),
  ('StPtb', 2),
  ('London', 4),
  ('Manchester', 4),
  ('Washington D.C.', 3),
  ('NewYork', 3);
INSERT INTO users (login, name, email, created) VALUES
  ('login', 'user name', 'email', '2000-01-01 00:00:00.000'),
  ('user', 'user second name', 'email', '2000-01-01 00:00:00.000'),
  ('admin', 'user other name', 'email', '2000-01-01 00:00:00.000');
INSERT INTO roles (name) VALUES
  ('ADMINISTRATOR'),
  ('DEFAULT_USER');
INSERT INTO use_role VALUES
  ( 3, 1);



