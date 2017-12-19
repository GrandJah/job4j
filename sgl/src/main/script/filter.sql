--type(id, name)
CREATE TABLE type(id SERIAL PRIMARY KEY , name VARCHAR(10) NOT NULL UNIQUE);
INSERT INTO type (name) VALUES ('СЫР'),('МОЛОКО'),('ЯЙЦО'),('МУКА'),('МЯСО'),('ВКУСНЯШКИ');
--product(id, name, type_id, expired_date, price)
CREATE FUNCTION getTypeID(VARCHAR(10)) RETURNS INTEGER LANGUAGE SQL AS 'SELECT id FROM type WHERE name=$1';
CREATE TABLE product(id SERIAL PRIMARY KEY , name VARCHAR(10), type_id INTEGER REFERENCES type(id) NOT NULL, expired_date date, price DEC(6,2));
INSERT INTO product (name,type_id,expired_date,price)  VALUES
  ('product1',getTypeID('СЫР'),'2017-12-15',1527.45),
  ('product2',getTypeID('МОЛОКО'),'2018-01-16',1452.45),
  ('product3',getTypeID('ЯЙЦО'),'2017-12-15',152.45),
  ('product4',getTypeID('СЫР'),'2018-01-16',1562.45),
  ('product5',getTypeID('МЯСО'),'2017-12-15',12.45),
  ('product6',getTypeID('МЯСО'),'2018-01-16',15.45),
  ('product7',getTypeID('СЫР'),'2017-12-15',152.5),
  ('мороженное',getTypeID('ВКУСНЯШКИ'),'2018-01-16',52.45);

-- запрос получение всех продуктов с типом "СЫР"
SELECT product.name FROM product JOIN type t ON product.type_id = t.id WHERE t.name='СЫР';

--запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT * FROM product WHERE name LIKE '%мороженное%';

--запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT * FROM product WHERE extract(MONTH FROM expired_date) = extract(MONTH FROM now() + INTERVAL '1 month');

--запрос, который вывод самый дорогой продукт.
SELECT * FROM product ORDER BY price DESC LIMIT 1;

--запрос, который выводит количество всех продуктов определенного типа.
SELECT t.name, count(t.name) FROM product JOIN type as t ON t.id = product.type_id GROUP BY (t.name) ORDER BY count(t.name) DESC;

--запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT name FROM product WHERE type_id = getTypeID('СЫР') OR type_id = getTypeID('МОЛОКО');
SELECT product.name FROM product JOIN type t ON product.type_id = t.id WHERE t.name='СЫР' or t.name='МОЛОКО';

--запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
SELECT t.name FROM product JOIN type as t ON t.id = product.type_id GROUP BY (t.name) HAVING count(t.name) < 10;

--Вывести все продукты и их тип.
SELECT product.name, type.name FROM product JOIN type ON type.id = product.type_id;

