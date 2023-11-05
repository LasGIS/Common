ALTER TABLE person ADD CONSTRAINT sk_person_unique UNIQUE (first_name, last_name, middle_name, sex);
