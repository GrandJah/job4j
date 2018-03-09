CREATE TABLE if not exists Users_store(
  login VARCHAR(30) PRIMARY KEY UNIQUE,
  name VARCHAR(50),
  email VARCHAR(30),
  created TIMESTAMP);
CREATE TABLE IF NOT EXISTS UseRole(
  user_login VARCHAR(30) REFERENCES Users_store(login) UNIQUE NOT NULL,
  role VARCHAR(30) NOT NULL);
DELETE FROM UseRole;
DELETE FROM Users_store;