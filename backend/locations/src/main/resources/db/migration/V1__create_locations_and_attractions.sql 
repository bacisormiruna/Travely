CREATE TABLE locations (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100)     NOT NULL,
    country     VARCHAR(100)     NOT NULL,
    latitude    DOUBLE PRECISION,
    longitude   DOUBLE PRECISION,
    created_at  TIMESTAMP        NOT NULL DEFAULT now(),
    CONSTRAINT uk_location_name_country UNIQUE (name, country)
);

CREATE TABLE attractions (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(150)     NOT NULL,
    description     TEXT             NOT NULL,
    category        VARCHAR(50)      NOT NULL,
    location_id     BIGINT           NOT NULL REFERENCES locations (id),
    latitude        DOUBLE PRECISION NOT NULL,
    longitude       DOUBLE PRECISION NOT NULL,
    entry_price     NUMERIC(10, 2),
    audio_file_path VARCHAR(255),
    created_at      TIMESTAMP        NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE INDEX idx_attractions_location ON attractions (location_id);
CREATE INDEX idx_attractions_category ON attractions (category);