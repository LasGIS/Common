--DROP SCHEMA IF EXISTS mybatis CASCADE;
--CREATE SCHEMA mybatis;
--==============================================================
-- Table: um_user
--==============================================================
CREATE TABLE um_user (
  umusr_user_id  SERIAL             NOT NULL,
  umusr_login    TEXT               NOT NULL UNIQUE,
  umusr_name     TEXT               NOT NULL,
  umusr_password TEXT,
  umusr_archived BOOL DEFAULT FALSE NOT NULL,
  CONSTRAINT um_user_pk PRIMARY KEY (umusr_user_id)
);
COMMENT ON TABLE um_user IS 'Таблица пользователей';
COMMENT ON COLUMN um_user.umusr_user_id IS 'Уникальный номер пользователя';
COMMENT ON COLUMN um_user.umusr_login IS 'Имя пользователя для входа в систему';
COMMENT ON COLUMN um_user.umusr_name IS 'ФИО пользователя';
COMMENT ON COLUMN um_user.umusr_password IS 'ФИО пользователя';
COMMENT ON COLUMN um_user.umusr_archived IS 'Признак архивации (не активная запись)';

--==============================================================
-- Table: um_role
--==============================================================
CREATE TABLE um_role (
  umrle_role_id     TEXT NOT NULL,
  umrle_description TEXT NOT NULL,
  CONSTRAINT um_role_pk PRIMARY KEY (umrle_role_id)
);
COMMENT ON TABLE um_role IS 'Таблица ролей';
COMMENT ON COLUMN um_role.umrle_role_id IS 'Уникальный номер роли';
COMMENT ON COLUMN um_role.umrle_description IS 'Описание роли';

--==============================================================
-- Table: um_user_role
--==============================================================
CREATE TABLE um_user_role (
  umurl_user_role_id SERIAL,
  umusr_user_id      INTEGER NOT NULL REFERENCES um_user (umusr_user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  umrle_role_id      TEXT    NOT NULL REFERENCES um_role (umrle_role_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT um_user_role_pk PRIMARY KEY (umurl_user_role_id),
  CONSTRAINT um_user_role_unique UNIQUE (umusr_user_id, umrle_role_id)
);
COMMENT ON TABLE um_user_role IS 'Таблица связи. Связывает пользователя со многими его ролями';
COMMENT ON COLUMN um_user_role.umusr_user_id IS 'Уникальный номер пользователя';
COMMENT ON COLUMN um_user_role.umrle_role_id IS 'Уникальный номер роли';

--==============================================================
-- Table: pr_person
--==============================================================
CREATE TABLE pr_person (
  prprs_person_id   SERIAL NOT NULL,
  prprs_mother_id   INTEGER REFERENCES pr_person (prprs_person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  prprs_father_id   INTEGER REFERENCES pr_person (prprs_person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  prprs_first_name  TEXT   NOT NULL,
  prprs_last_name   TEXT   NOT NULL,
  prprs_middle_name TEXT,
  prprs_sex TEXT UNIQUE NOT NULL CHECK (prprs_sex IN (
    'MALE',  -- мужчина
    'FEMALE' -- женщина
  )),
  CONSTRAINT pr_person_pk PRIMARY KEY (prprs_person_id)
);
COMMENT ON TABLE pr_person IS 'Таблица персон';
COMMENT ON COLUMN pr_person.prprs_person_id IS 'Уникальный номер персоны';
COMMENT ON COLUMN pr_person.prprs_mother_id IS 'Мать';
COMMENT ON COLUMN pr_person.prprs_father_id IS 'Отец';
COMMENT ON COLUMN pr_person.prprs_first_name IS 'Имя';
COMMENT ON COLUMN pr_person.prprs_last_name IS 'Фамилия';
COMMENT ON COLUMN pr_person.prprs_middle_name IS 'Отчество';

--==============================================================
-- Table: pr_person_relation
--==============================================================
CREATE TABLE pr_person_relation (
  prprl_person_relation_id SERIAL,
  prprs_person_id          INTEGER     NOT NULL REFERENCES pr_person (prprs_person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  prprl_person_to_id       INTEGER     NOT NULL REFERENCES pr_person (prprs_person_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  prprl_type               TEXT UNIQUE NOT NULL CHECK (prprl_type IN (
    'SPOUSE',          -- супруг (муж или жена)
    'SIBLING',         -- родной брат или сестра (если отец или мать неизвестны)
    'RELATIVE',        -- родственник, родственница
    'COLLEAGUE'        -- коллега
  )),
  CONSTRAINT pr_person_role_pk PRIMARY KEY (prprl_person_relation_id),
  CONSTRAINT pr_person_role_unique UNIQUE (prprs_person_id, prprl_person_to_id, prprl_type)
);
COMMENT ON TABLE pr_person_relation IS 'Таблица связи. Связывает одну персону с другими персонами';
COMMENT ON COLUMN pr_person_relation.prprl_person_relation_id IS 'Уникальный номер связи';
COMMENT ON COLUMN pr_person_relation.prprs_person_id          IS 'Id основной персоны';
COMMENT ON COLUMN pr_person_relation.prprl_person_to_id       IS 'Id связанной персоны';
COMMENT ON COLUMN pr_person_relation.prprl_type               IS 'тип связи';
