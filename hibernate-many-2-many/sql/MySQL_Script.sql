-- DROP SCHEMA IF EXISTS hib_test CASCADE;
-- CREATE SCHEMA hib_test AUTHORIZATION postgres;

CREATE TABLE users
(
    user_id  serial      NOT NULL,
    username varchar(45) NOT NULL,
    password varchar(45) NOT NULL,
    email    varchar(45) NOT NULL,
    CONSTRAINT um_user_pk PRIMARY KEY (user_id)
);


CREATE TABLE groups
(
    group_id serial      NOT NULL,
    name     varchar(45) NOT NULL,
    PRIMARY KEY (group_id)
);

CREATE TABLE users_groups
(
    user_group_id   serial  NOT NULL,
    user_id         bigint  NOT NULL,
    group_id        bigint  NOT NULL,
    activated       boolean NOT NULL,
    registered_date date    NOT NULL,
    PRIMARY KEY (user_group_id),
    CONSTRAINT fk_users_groups_group FOREIGN KEY (group_id) REFERENCES groups (group_id),
    CONSTRAINT fk_users_groups_user FOREIGN KEY (user_id) REFERENCES users (user_id)
)

