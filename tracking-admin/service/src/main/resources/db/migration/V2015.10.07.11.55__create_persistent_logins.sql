CREATE TABLE IF NOT EXISTS persistent_logins
(
    "series"    VARCHAR(255) NOT NULL,
    "username"  VARCHAR(255) NOT NULL,
    "token"     VARCHAR(255) NOT NULL,
    "last_used" TIMESTAMP    NOT NULL,
    PRIMARY KEY ("series")
);
