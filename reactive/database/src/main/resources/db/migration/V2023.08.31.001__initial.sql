--==============================================================
-- Table: user
--==============================================================
CREATE TABLE "user" (
    user_id  SERIAL             NOT NULL,
    login    TEXT               NOT NULL UNIQUE,
    name     TEXT               NOT NULL,
    password TEXT,
    archived bool DEFAULT FALSE NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);
COMMENT ON TABLE "user" IS 'Таблица пользователей';
COMMENT ON COLUMN "user".user_id IS 'Уникальный номер пользователя';
COMMENT ON COLUMN "user".login IS 'Имя пользователя для входа в систему';
COMMENT ON COLUMN "user".name IS 'ФИО пользователя';
COMMENT ON COLUMN "user".password IS 'ФИО пользователя';
COMMENT ON COLUMN "user".archived IS 'Признак архивации (не активная запись)';

--==============================================================
-- Table: user_role
--==============================================================
CREATE TABLE user_role (
    user_id      INTEGER NOT NULL REFERENCES "user" (user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    role         TEXT    NOT NULL CHECK (role IN (
        'ADMIN',      -- Администратор
        'CHIEF',      -- Начальник
        'OPERATOR',   -- Оператор
        'SUPERVISOR'  -- Старший смены
    )),
    CONSTRAINT user_role_pk PRIMARY KEY (user_id, role)
);
COMMENT ON TABLE user_role          IS 'Таблица связи. Связывает пользователя со многими его ролями';
COMMENT ON COLUMN user_role.user_id IS 'Уникальный номер пользователя';
COMMENT ON COLUMN user_role.role    IS 'Уникальный идентификатор роли';
