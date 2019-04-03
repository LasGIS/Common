INSERT INTO um_role (UMRLE_ROLE_ID, UMRLE_DESCRIPTION) VALUES ('OPERATOR', 'Оператор');
INSERT INTO um_role (UMRLE_ROLE_ID, UMRLE_DESCRIPTION) VALUES ('SUPERVISOR', 'Старший смены');
INSERT INTO um_role (UMRLE_ROLE_ID, UMRLE_DESCRIPTION) VALUES ('CHIEF', 'Начальник');
INSERT INTO um_role (UMRLE_ROLE_ID, UMRLE_DESCRIPTION) VALUES ('ADMIN', 'Администратор');

INSERT INTO um_user (umusr_user_id, umusr_login, umusr_name, umusr_password) VALUES (1, 'LasGIS', 'Владимир Ласкин', '123' );
INSERT INTO um_user (umusr_user_id, umusr_login, umusr_name, umusr_password) VALUES (2, 'VPupkin', 'Василий Пупкин', '321' );

INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (1, 'ADMIN');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (1, 'CHIEF');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (1, 'SUPERVISOR');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (2, 'SUPERVISOR');
INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (2, 'OPERATOR');