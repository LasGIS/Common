/*
 *  @(#)V2023.06.01.18.44__insert_users.sql  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

INSERT INTO um_user (umusr_user_id, umusr_login, umusr_name, umusr_password, umusr_archived/*, umusr_roles*/)
VALUES (1, 'LasGIS', 'Владимир Ласкин', '123', FALSE/*, '{"ADMIN", "CHIEF", "SUPERVISOR"}'*/);
INSERT INTO um_user (umusr_user_id, umusr_login, umusr_name, umusr_password, umusr_archived/*, umusr_roles*/)
VALUES (2, 'VPupkin', 'Василий Пупкин', '321', FALSE/*, '{"SUPERVISOR", "OPERATOR"}'*/);

INSERT INTO groups (group_id, name) VALUES (1, 'group1');
INSERT INTO groups (group_id, name) VALUES (2, 'group2');

INSERT INTO users_groups (user_group_id, group_id, user_id, activated, registered_date) VALUES (1, 1, 1, false, current_date);
INSERT INTO users_groups (user_group_id, group_id, user_id, activated, registered_date) VALUES (2, 1, 2, false, current_date);
INSERT INTO users_groups (user_group_id, group_id, user_id, activated, registered_date) VALUES (3, 2, 1, false, current_date);
INSERT INTO users_groups (user_group_id, group_id, user_id, activated, registered_date) VALUES (4, 2, 2, false, current_date);

