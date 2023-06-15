INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$RFKyMoxwTMxfwFmrhcQr3ukbXibYO6pprNEprRPXMbu0NPIgi.f0C', true);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$RFKyMoxwTMxfwFmrhcQr3ukbXibYO6pprNEprRPXMbu0NPIgi.f0C', true);

INSERT INTO authorities (username, authority) VALUES ('admin', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin', 'USER');
INSERT INTO authorities (username, authority) VALUES ('user', 'USER');
