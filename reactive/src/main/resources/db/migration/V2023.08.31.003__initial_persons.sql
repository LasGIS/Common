/*
 *  @(#)V2023.08.31.003__initial_persons.sql  last: 01.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

/*
 *  @(#)V2023.08.31.003__initial_persons.sql  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

--==============================================================
-- Table: person
--==============================================================
CREATE TABLE person (
  person_id   SERIAL NOT NULL,
  mother_id   INTEGER REFERENCES person (person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  father_id   INTEGER REFERENCES person (person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  first_name  TEXT   NOT NULL,
  last_name   TEXT   NOT NULL,
  middle_name TEXT,
  sex TEXT NOT NULL CHECK (sex IN (
    'MALE',  -- мужчина
    'FEMALE' -- женщина
  )),
  CONSTRAINT person_pk PRIMARY KEY (person_id)
);
COMMENT ON TABLE person IS 'Таблица персон';
COMMENT ON COLUMN person.person_id IS 'Уникальный номер персоны';
COMMENT ON COLUMN person.mother_id IS 'Мать';
COMMENT ON COLUMN person.father_id IS 'Отец';
COMMENT ON COLUMN person.first_name IS 'Имя';
COMMENT ON COLUMN person.last_name IS 'Фамилия';
COMMENT ON COLUMN person.middle_name IS 'Отчество';

--==============================================================
-- Table: person_relation
--==============================================================
CREATE TABLE person_relation (
  person_relation_id SERIAL,
  person_id          INTEGER     NOT NULL REFERENCES person (person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  person_to_id       INTEGER     NOT NULL REFERENCES person (person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  "type"               TEXT UNIQUE NOT NULL CHECK ("type" IN (
    'SPOUSE',          -- супруг (муж или жена)
    'SIBLING',         -- родной брат или сестра (если отец или мать неизвестны)
    'RELATIVE',        -- родственник, родственница
    'COLLEAGUE'        -- коллега
  )),
  CONSTRAINT person_role_pk PRIMARY KEY (person_relation_id),
  CONSTRAINT person_role_unique UNIQUE (person_id, person_to_id, "type")
);
COMMENT ON TABLE person_relation IS 'Таблица связи. Связывает одну персону с другими персонами';
COMMENT ON COLUMN person_relation.person_relation_id IS 'Уникальный номер связи';
COMMENT ON COLUMN person_relation.person_id          IS 'Id основной персоны';
COMMENT ON COLUMN person_relation.person_to_id       IS 'Id связанной персоны';
COMMENT ON COLUMN person_relation."type"             IS 'тип связи';
