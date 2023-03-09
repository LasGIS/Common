INSERT INTO service_users ("username",
                           "password",
                           "enabled",
                           "accountNonExpired",
                           "credentialsNonExpired",
                           "accountNonLocked",
                           "authorityString",
                           "name",
                           "surname",
                           "patronymic",
                           "email",
                           "affiliate")
VALUES ('arseny', 'h0%Ye1LvD2*4', TRUE, TRUE, TRUE, TRUE, 'ROLE_ADMIN', NULL, NULL, NULL, NULL, NULL)
ON CONFLICT DO NOTHING;
