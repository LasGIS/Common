/*
 *  @(#)V2023.08.31.002__insert_users.sql  last: 01.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

/*
 *  @(#)V2023.08.31.002__insert_users.sql  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

INSERT INTO role (role_id, description) VALUES ('OPERATOR', 'Оператор');
INSERT INTO role (role_id, description) VALUES ('SUPERVISOR', 'Старший смены');
INSERT INTO role (role_id, description) VALUES ('CHIEF', 'Начальник');
INSERT INTO role (role_id, description) VALUES ('ADMIN', 'Администратор');

INSERT INTO "user" (login, name, password) VALUES ('LasGIS', 'Владимир Ласкин', '123');
INSERT INTO "user" (login, name, password) VALUES ('VPupkin', 'Василий Пупкин', '321');

INSERT INTO user_role (user_id, role_id) VALUES (1, 'ADMIN');
INSERT INTO user_role (user_id, role_id) VALUES (1, 'CHIEF');
INSERT INTO user_role (user_id, role_id) VALUES (1, 'SUPERVISOR');
INSERT INTO user_role (user_id, role_id) VALUES (2, 'SUPERVISOR');
INSERT INTO user_role (user_id, role_id) VALUES (2, 'OPERATOR');