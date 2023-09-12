INSERT INTO person (person_id, first_name, middle_name, last_name, sex)
VALUES (1, 'Влади́мир', 'Владимирович', 'Маяковский', 'MALE'),
       (2, 'Владимир', 'Константинович', 'Маяковский', 'MALE'),
       (3, 'Александра', 'Алексеевна', 'Павленко', 'FEMALE');

INSERT INTO person_relation (person_id, person_to_id, type)
VALUES (1, 2, 'PARENT'),
       (1, 3, 'PARENT'),
       (2, 1, 'CHILD'),
       (3, 1, 'CHILD');