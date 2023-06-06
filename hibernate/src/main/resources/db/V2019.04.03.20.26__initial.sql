DROP SCHEMA IF EXISTS hiber CASCADE;
CREATE SCHEMA hiber AUTHORIZATION postgres;

/*
 *  @(#)V2019.04.03.20.26__initial.sql  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

--==============================================================
-- Table: um_user
--==============================================================
CREATE TABLE um_user (
  umusr_user_id      SERIAL NOT NULL,
  umusr_login        TEXT NOT NULL UNIQUE,
  umusr_name         TEXT NOT NULL,
  umusr_password     TEXT,
  umusr_archived     BOOL DEFAULT false NOT NULL,
  umusr_roles        TEXT[],
  CONSTRAINT um_user_pk PRIMARY KEY (umusr_user_id)
);
COMMENT ON TABLE  um_user                    IS 'Таблица пользователей';
COMMENT ON COLUMN um_user.umusr_user_id      IS 'Уникальный номер пользователя';
COMMENT ON COLUMN um_user.umusr_login        IS 'Имя пользователя для входа в систему';
COMMENT ON COLUMN um_user.umusr_name         IS 'ФИО пользователя';
COMMENT ON COLUMN um_user.umusr_password     IS 'ФИО пользователя';
COMMENT ON COLUMN um_user.umusr_archived     IS 'Признак архивации (не активная запись)';
