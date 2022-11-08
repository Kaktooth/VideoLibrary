CREATE TABLE authorities
(
    id        UUID PRIMARY KEY,
    authority INTEGER NOT NULL REFERENCES user_authorities (id),
    username  VARCHAR NOT NULL
);