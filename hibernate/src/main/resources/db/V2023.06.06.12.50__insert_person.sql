INSERT INTO pr_person (prprs_person_id, prprs_first_name, prprs_last_name, prprs_middle_name, prprs_gender)
VALUES (1, 'Владимир', 'Маяковский', 'Константинович', 'MALE');
INSERT INTO pr_person (prprs_person_id, prprs_first_name, prprs_last_name, prprs_middle_name, prprs_gender)
VALUES (2, 'Александра', 'Павленко', 'Алексеевна', 'FEMALE');
INSERT INTO pr_person (prprs_person_id, prprs_first_name, prprs_last_name, prprs_middle_name, prprs_gender)
VALUES (3, 'Владимир', 'Маяковский', 'Владимирович', 'MALE');

INSERT INTO pr_person_relation (prprl_person_relation_id, prprl_relation_type, prprl_person_from_id, prprl_person_to_id) VALUES (1, 'PARENT', 1, 3);
INSERT INTO pr_person_relation (prprl_person_relation_id, prprl_relation_type, prprl_person_from_id, prprl_person_to_id) VALUES (2, 'PARENT', 2, 3);
