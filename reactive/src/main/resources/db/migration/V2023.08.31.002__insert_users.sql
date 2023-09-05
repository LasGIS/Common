INSERT INTO role (role_id, description) VALUES ('OPERATOR', 'Оператор');
INSERT INTO role (role_id, description) VALUES ('SUPERVISOR', 'Старший смены');
INSERT INTO role (role_id, description) VALUES ('CHIEF', 'Начальник');
INSERT INTO role (role_id, description) VALUES ('ADMIN', 'Администратор');

INSERT INTO "user" (user_id, login, name, password) VALUES (1, 'LasGIS', 'Владимир Ласкин', '123');
INSERT INTO "user" (user_id, login, name, password) VALUES (2, 'VPupkin', 'Василий Пупкин', '321');

INSERT INTO user_role (user_id, role_id) VALUES (1, 'ADMIN');
INSERT INTO user_role (user_id, role_id) VALUES (1, 'CHIEF');
INSERT INTO user_role (user_id, role_id) VALUES (1, 'SUPERVISOR');
INSERT INTO user_role (user_id, role_id) VALUES (2, 'SUPERVISOR');
INSERT INTO user_role (user_id, role_id) VALUES (2, 'OPERATOR');