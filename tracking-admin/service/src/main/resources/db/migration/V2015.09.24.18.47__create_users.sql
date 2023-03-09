CREATE TABLE IF NOT EXISTS service_users
(
    "username"              VARCHAR(255) NOT NULL,
    "password"              VARCHAR(255) NOT NULL,
    "enabled"               BOOLEAN      NOT NULL DEFAULT TRUE,
    "accountNonExpired"     BOOLEAN      NOT NULL DEFAULT TRUE,
    "credentialsNonExpired" BOOLEAN      NOT NULL DEFAULT TRUE,
    "accountNonLocked"      BOOLEAN      NOT NULL DEFAULT TRUE,
    "authorityString"       VARCHAR(255),
    "name"                  VARCHAR(255),
    "surname"               VARCHAR(255),
    "patronymic"            VARCHAR(255),
    "email"                 VARCHAR(255),
    "affiliate"             VARCHAR(255),
    PRIMARY KEY ("username")
);
