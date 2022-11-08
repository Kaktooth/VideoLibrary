CREATE TABLE videos
(
    id          UUID PRIMARY KEY,
    video_id    VARCHAR(11) UNIQUE NOT NULL,
    category_id UUID REFERENCES categories (id) ON DELETE CASCADE
);