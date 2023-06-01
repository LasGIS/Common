/*
 *  @(#)V2__insert_users.sql  last: 01.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

INSERT INTO um_user (umusr_login, umusr_name, umusr_password, umusr_archived, umusr_roles)
VALUES ('LasGIS', 'Владимир Ласкин', '123', FALSE, '{"ADMIN", "CHIEF", "SUPERVISOR"}');
INSERT INTO um_user (umusr_login, umusr_name, umusr_password, umusr_archived, umusr_roles)
VALUES ('VPupkin', 'Василий Пупкин', '321', FALSE, '{"SUPERVISOR", "OPERATOR"}');
