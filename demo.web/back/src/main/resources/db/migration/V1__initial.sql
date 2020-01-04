--DROP SCHEMA IF EXISTS hiber CASCADE;
--CREATE SCHEMA hiber AUTHORIZATION postgres;

--==============================================================
-- Table: um_user
--==============================================================
CREATE TABLE um_user (
  umusr_user_id      SERIAL NOT NULL,
  umusr_login        TEXT NOT NULL UNIQUE,
  umusr_name         TEXT NOT NULL,
  umusr_password     TEXT,
  umusr_archived     BOOL DEFAULT false NOT NULL,
  CONSTRAINT um_user_pk PRIMARY KEY (umusr_user_id)
);
COMMENT ON TABLE  um_user                    IS 'Таблица пользователей';
COMMENT ON COLUMN um_user.umusr_user_id      IS 'Уникальный номер пользователя';
COMMENT ON COLUMN um_user.umusr_login        IS 'Имя пользователя для входа в систему';
COMMENT ON COLUMN um_user.umusr_name         IS 'ФИО пользователя';
COMMENT ON COLUMN um_user.umusr_password     IS 'ФИО пользователя';
COMMENT ON COLUMN um_user.umusr_archived     IS 'Признак архивации (не активная запись)';

--==============================================================
-- Table: um_role
--==============================================================
CREATE TABLE um_role (
  umrle_role_id        TEXT NOT NULL,
  umrle_description    TEXT NOT NULL,
  CONSTRAINT um_role_pk PRIMARY KEY (umrle_role_id)
);
COMMENT ON TABLE  um_role                    IS 'Таблица ролей';
COMMENT ON COLUMN um_role.umrle_role_id      IS 'Уникальный номер роли';
COMMENT ON COLUMN um_role.umrle_description  IS 'Описание роли';

--==============================================================
-- Table: um_user_role
--==============================================================
CREATE TABLE um_user_role (
  umurl_user_role_id SERIAL,
  umusr_user_id      INTEGER NOT NULL REFERENCES um_user(umusr_user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  umrle_role_id      TEXT NOT NULL REFERENCES um_role(umrle_role_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT um_user_role_pk PRIMARY KEY (umurl_user_role_id),
  CONSTRAINT um_user_role_unique UNIQUE (umusr_user_id, umrle_role_id)
);
COMMENT ON TABLE  um_user_role                    IS 'Таблица связи. Связывает пользователя со многими его ролями';
COMMENT ON COLUMN um_user_role.umusr_user_id      IS 'Уникальный номер пользователя';
COMMENT ON COLUMN um_user_role.umrle_role_id      IS 'Уникальный номер роли';
