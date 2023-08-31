--==============================================================
-- Table: user
--==============================================================
CREATE TABLE "user" (
  user_id  SERIAL             NOT NULL,
  login    TEXT               NOT NULL UNIQUE,
  name     TEXT               NOT NULL,
  password TEXT,
  archived BOOL DEFAULT FALSE NOT NULL,
  CONSTRAINT user_pk PRIMARY KEY (user_id)
);
COMMENT ON TABLE "user" IS 'Таблица пользователей';
COMMENT ON COLUMN "user".user_id IS 'Уникальный номер пользователя';
COMMENT ON COLUMN "user".login IS 'Имя пользователя для входа в систему';
COMMENT ON COLUMN "user".name IS 'ФИО пользователя';
COMMENT ON COLUMN "user".password IS 'ФИО пользователя';
COMMENT ON COLUMN "user".archived IS 'Признак архивации (не активная запись)';

--==============================================================
-- Table: role
--==============================================================
CREATE TABLE role (
  role_id     TEXT NOT NULL,
  description TEXT NOT NULL,
  CONSTRAINT role_pk PRIMARY KEY (role_id)
);
COMMENT ON TABLE role IS 'Таблица ролей';
COMMENT ON COLUMN role.role_id IS 'Уникальный номер роли';
COMMENT ON COLUMN role.description IS 'Описание роли';

/*
 *  @(#)V2023.08.31_01__initial.sql  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

--==============================================================
-- Table: user_role
--==============================================================
CREATE TABLE user_role (
  user_role_id SERIAL,
  user_id      INTEGER NOT NULL REFERENCES "user" (user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  role_id      TEXT    NOT NULL REFERENCES role (role_id) ON UPDATE RESTRICT ON DELETE CASCADE,
  CONSTRAINT user_role_pk PRIMARY KEY (user_role_id),
  CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);
COMMENT ON TABLE user_role IS 'Таблица связи. Связывает пользователя со многими его ролями';
COMMENT ON COLUMN user_role.user_id IS 'Уникальный номер пользователя';
COMMENT ON COLUMN user_role.role_id IS 'Уникальный номер роли';
