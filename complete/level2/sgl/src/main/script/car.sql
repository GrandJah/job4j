--Создать структур данных в базе. Таблицы. Трансмиссия. Двигатель, Коробка передач.
CREATE TABLE transmission (id SERIAL PRIMARY KEY, name CHAR(10) NOT NULL UNIQUE );
CREATE TABLE carcass (id SERIAL PRIMARY KEY, name CHAR(10) NOT NULL UNIQUE );
CREATE TABLE engine (id SERIAL PRIMARY KEY, name CHAR(10) NOT NULL UNIQUE );

--Создать структуру Машина. Машина не может существовать без данных из первого задания.
CREATE TABLE car (id SERIAL PRIMARY KEY, name CHAR(10) NOT NULL UNIQUE,
  transmission INTEGER REFERENCES transmission(id) NOT NULL UNIQUE,
  carcass INTEGER REFERENCES carcass(id) NOT NULL UNIQUE,
  engine INTEGER REFERENCES engine(id) NOT NULL UNIQUE);

--Вывести все машины.
SELECT car.name, t.name, c.name, e.name FROM car
  join transmission t ON car.transmission = t.id
  join carcass c ON car.carcass = c.id
  join engine e ON car.engine = e.id;

--Вывести все неиспользуемые детали.
SELECT transmission.name FROM transmission LEFT JOIN car ON transmission.id = car.transmission WHERE car.name ISNULL;
SELECT carcass.name FROM carcass LEFT JOIN car ON carcass.id = car.carcass WHERE car.name ISNULL;
SELECT engine.name FROM engine LEFT JOIN car ON engine.id = car.engine WHERE car.name ISNULL;