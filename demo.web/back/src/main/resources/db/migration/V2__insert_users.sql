INSERT INTO um_role (umrle_role_id, umrle_description) VALUES ('OPERATOR', 'Оператор');
INSERT INTO um_role (umrle_role_id, umrle_description) VALUES ('SUPERVISOR', 'Старший смены');
INSERT INTO um_role (umrle_role_id, umrle_description) VALUES ('CHIEF', 'Начальник');
INSERT INTO um_role (umrle_role_id, umrle_description) VALUES ('ADMIN', 'Администратор');

INSERT INTO um_user (umusr_login, umusr_name, umusr_password) VALUES ('LasGIS', 'Владимир Ласкин', '123' );
INSERT INTO um_user (umusr_login, umusr_name, umusr_password) VALUES ('VPupkin', 'Василий Пупкин', '321' );

INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (1, 'ADMIN');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (1, 'CHIEF');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (1, 'SUPERVISOR');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (2, 'SUPERVISOR');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (2, 'OPERATOR');