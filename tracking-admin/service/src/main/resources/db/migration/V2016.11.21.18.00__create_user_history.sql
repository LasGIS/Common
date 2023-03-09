CREATE TABLE IF NOT EXISTS service_user_history_event_type
(
    name VARCHAR(70) NOT NULL,
    PRIMARY KEY ("name")
);

INSERT INTO service_user_history_event_type (name)
VALUES ('USER_ROLES_UPDATED'),
       ('USER_INFORMATION_UPDATED'),
       ('USER_CREATED'),
       ('USER_PASSWORD_RESET'),
       ('USER_DELETED')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS service_user_history
(
    id         BIGSERIAL,
    username   VARCHAR(255) NOT NULL,
    datetime   TIMESTAMP    NOT NULL,
    author     VARCHAR(255),
    event_type VARCHAR(70)  NOT NULL,
    event_args TEXT,
    comment    VARCHAR(255),
    PRIMARY KEY ("id"),
    FOREIGN KEY ("event_type") REFERENCES service_user_history_event_type ("name")
);
