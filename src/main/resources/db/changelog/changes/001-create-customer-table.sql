CREATE TABLE customer
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(255) NOT NULL,
    age          INTEGER      NOT NULL,
    metadata     JSON,
    payment_type VARCHAR(90),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    created_by   VARCHAR(255),
    updated_by   VARCHAR(255)
);