-- Remove duplicate usernames (ignore case), keep rows having longer authorityString
DELETE
FROM service_users
WHERE username IN (SELECT username
                   FROM (SELECT username,
                                "authorityString",
                                ROW_NUMBER()
                                OVER (
                                    PARTITION BY LOWER(username)
                                    ORDER BY CHAR_LENGTH("authorityString") DESC, username ASC ) AS rnum
                         FROM service_users) t
                   WHERE t.rnum > 1);

-- Recreate index by username, ignoring case
CREATE UNIQUE INDEX IF NOT EXISTS service_users_username_unique ON service_users (LOWER(username));

-- Update username to lower case in all tables
UPDATE service_user_history SET username = LOWER(username);
UPDATE persistent_logins SET username = LOWER(username);
UPDATE service_users SET username = LOWER(username);
