INSERT INTO person (person_id, first_name, middle_name, last_name, sex)
VALUES (1, 'Владимир', 'Владимирович', 'Маяковский', 'MALE'),
       (2, 'Владимир', 'Константинович', 'Маяковский', 'MALE'),
       (3, 'Александра', 'Алексеевна', 'Павленко', 'FEMALE');

SELECT SETVAL('person_person_id_seq', 4);
COMMENT ON SEQUENCE person_person_id_seq IS 'генератор последовательности ID для person.person_id';

INSERT INTO person_relation (person_id, person_to_id, type)
VALUES (1, 2, 'PARENT'),
       (1, 3, 'PARENT'),
       (2, 1, 'CHILD'),
       (3, 1, 'CHILD'),
       (2, 3, 'SPOUSE'),
       (3, 2, 'SPOUSE');
