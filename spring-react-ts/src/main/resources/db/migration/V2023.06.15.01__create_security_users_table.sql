DROP INDEX IF EXISTS ix_auth_username CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;
--==============================================================
-- Table: users
--==============================================================
CREATE TABLE users
(
    username varchar(50)  NOT NULL PRIMARY KEY,
    password varchar(500) NOT NULL,
    enabled  boolean      NOT NULL
);
COMMENT ON TABLE users IS 'Таблица персон';
COMMENT ON COLUMN users.username IS 'login пользователя';
COMMENT ON COLUMN users.password IS 'пароль в формате ??';
COMMENT ON COLUMN users.enabled IS 'признак правоспособности';

--==============================================================
-- Table: authorities
--==============================================================
CREATE TABLE authorities
(
    username  varchar(50) NOT NULL,
    authority varchar(20) NOT NULL CHECK (authority IN ('ADMIN', 'USER', 'OPERATOR', 'SUPERVISOR')),
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
COMMENT ON TABLE authorities IS 'Таблица ролей (полномочий) пользователей';
COMMENT ON COLUMN authorities.username IS 'login пользователя';
COMMENT ON COLUMN authorities.authority IS 'Роль пользователя. могут быть следующие роли: "ADMIN", "USER", "OPERATOR", "SUPERVISOR"';

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);