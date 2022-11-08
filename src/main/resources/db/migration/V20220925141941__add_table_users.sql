CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    username   VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    enabled    BOOLEAN NOT NULL
);