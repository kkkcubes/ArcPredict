CREATE TABLE markets (
    market_id BIGINT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    creator VARCHAR(255) NOT NULL,
    end_time BIGINT NOT NULL,
    yes_pool BIGINT NOT NULL DEFAULT 0,
    no_pool BIGINT NOT NULL DEFAULT 0,
    resolved BOOLEAN NOT NULL DEFAULT FALSE,
    outcome BOOLEAN,
    total_volume BIGINT DEFAULT 0,
    participants BIGINT DEFAULT 0,
    created_at TIMESTAMP
);