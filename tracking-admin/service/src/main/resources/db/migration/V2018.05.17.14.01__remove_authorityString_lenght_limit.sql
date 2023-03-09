-- Remove authorityString length limit
ALTER TABLE service_users ALTER COLUMN "authorityString" TYPE VARCHAR;
