/*
 *  @(#)V2023.08.31_04__insert_persons.sql  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

INSERT INTO person (person_id, first_name, last_name, middle_name, sex)
VALUES (1, 'Влади́мир', 'Владимирович', 'Маяковский', 'MALE');
INSERT INTO person (person_id, first_name, last_name, middle_name, sex)
VALUES (2, 'Владимир', 'Константинович', 'Маяковский', 'MALE');
INSERT INTO person (person_id, first_name, last_name, middle_name, sex)
VALUES (3, 'Александра Алексеевна', 'Константинович', 'Павленко', 'FEMALE');

UPDATE person SET father_id = 2, mother_id = 3 WHERE person_id = 1;
