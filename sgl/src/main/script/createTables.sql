CREATE TABLE role
(
  id SERIAL PRIMARY KEY,
  role_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users
(
  id SERIAL PRIMARY KEY,
  login VARCHAR(2000) NOT NULL UNIQUE,
  password VARCHAR(2000),
  role INTEGER REFERENCES role(id)  NOT NULL,
  create_date TIMESTAMP DEFAULT now()
);

CREATE TABLE state
(
  id SERIAL PRIMARY KEY,
  state VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE category
(
  id SERIAL PRIMARY KEY,
  name_category VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(50)
);

CREATE TABLE items
(
  id SERIAL PRIMARY KEY,
  state INTEGER REFERENCES state(id) NOT NULL,
  category INTEGER REFERENCES category(id) NOT NULL,
  create_user INTEGER REFERENCES users(id) NOT NULL,
  description VARCHAR(500) NOT NULL,
  create_date TIMESTAMP DEFAULT now()
);


CREATE TABLE comments
(
  id SERIAL PRIMARY KEY,
  description VARCHAR(500) NOT NULL,
  items_id INTEGER REFERENCES items(id)  NOT NULL,
  text VARCHAR(2000),
  create_date TIMESTAMP DEFAULT now()
);

CREATE TABLE attach
(
  id SERIAL PRIMARY KEY,
  description VARCHAR(500) NOT NULL,
  items_id INTEGER REFERENCES items(id) NOT NULL,
  data bytea,
  create_date TIMESTAMP DEFAULT now()
);

CREATE TABLE rules
(
  id SERIAL PRIMARY KEY,
  description VARCHAR(100)
);

CREATE TABLE r_role_rules
(
  id SERIAL PRIMARY KEY,
  role_id INTEGER REFERENCES role(id)  NOT NULL,
  rules_id INTEGER REFERENCES rules(id)  NOT NULL
);
