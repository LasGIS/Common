INSERT INTO person (person_id, first_name, last_name, middle_name, sex)
VALUES (1, 'Влади́мир', 'Владимирович', 'Маяковский', 'MALE');
INSERT INTO person (person_id, first_name, last_name, middle_name, sex)
VALUES (2, 'Владимир', 'Константинович', 'Маяковский', 'MALE');
INSERT INTO person (person_id, first_name, last_name, middle_name, sex)
VALUES (3, 'Александра Алексеевна', 'Константинович', 'Павленко', 'FEMALE');

UPDATE person SET father_id = 2, mother_id = 3 WHERE person_id = 1;
