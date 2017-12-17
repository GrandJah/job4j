INSERT INTO role(role_name) VALUES ('new');

CREATE FUNCTION newUser_role() RETURNS INT AS 'SELECT id FROM role WHERE role_name = ''new''' LANGUAGE SQL;

ALTER TABLE users ALTER COLUMN role SET DEFAULT newUser_role();

INSERT INTO users(login, password)
  VALUES ('unknown', ''),('user2','pass'),('user3','pass');

INSERT INTO state(state)
  VALUES ('new'),('execution'),('done');

INSERT INTO category(name_category)
  VALUES ('general');

CREATE FUNCTION state_new() RETURNS INT AS 'SELECT id FROM state WHERE state = ''new''' LANGUAGE SQL;
CREATE FUNCTION general_category() RETURNS INT AS 'SELECT id FROM category WHERE name_category = ''general''' LANGUAGE SQL;
CREATE FUNCTION unknown_user() RETURNS INT AS 'SELECT id FROM category WHERE name_category = ''general''' LANGUAGE SQL;

ALTER TABLE items
  ALTER COLUMN state SET DEFAULT state_new(),
  ALTER COLUMN category SET DEFAULT general_category(),
  ALTER COLUMN create_user SET DEFAULT unknown_user();

INSERT INTO items(description) VALUES ('заявочка');