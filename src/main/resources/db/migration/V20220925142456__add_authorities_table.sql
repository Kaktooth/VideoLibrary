CREATE TABLE authorities
(
    id           UUID PRIMARY KEY,
    authority_id INTEGER NOT NULL REFERENCES user_authorities (id),
    user_id      UUID REFERENCES users (id)
);