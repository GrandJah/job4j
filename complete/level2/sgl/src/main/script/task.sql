CREATE TABLE company
(
  id integer NOT NULL,
  name character varying,
  CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
  id integer NOT NULL,
  name character varying,
  company_id integer,
  CONSTRAINT person_pkey PRIMARY KEY (id)
);
--есть две таблицы )
--нужно
-- 1) Получить в одном запросе::
-- names of all persons that are NOT in the company with id = 5
-- имена всех лиц, которые НЕ принадлежат компании с id = 5
-- название компании для каждого человека
-- 2) Select the name of the company with the maximum number of persons +
-- number of persons in this company
--  Выберите название компании с максимальным
--  количеством лиц + количество человек в этой компании


SELECT p.name, c.name FROM person p LEFT JOIN company c ON c.id = p.company_id WHERE c.id <> 5;

SELECT c.name, count(p.id) as persons FROM company c JOIN  person p ON c.id = p.company_id
GROUP BY (c.id) ORDER BY count(p.id) DESC LIMIT 1;

SELECT name, count_of_person
FROM (SELECT company.name, count(*)as count_of_person
      FROM person LEFT JOIN company
          ON person.company_id = company.id
      GROUP BY company.name
     ) tab_1
  JOIN (
         SELECT max(my_count) AS max_count
         FROM
           (SELECT
              company.name, count(*) AS my_count
            FROM person LEFT JOIN company
                ON person.company_id = company.id
            GROUP BY company.name
           ) tab_0
       ) tab_2
    ON tab_1.count_of_person = tab_2.max_count;