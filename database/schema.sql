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

CREATE TABLE trades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    market_id BIGINT NOT NULL,
    trader VARCHAR(255),
    yes_position BOOLEAN,
    amount BIGINT,
    tx_hash VARCHAR(255),
    block_number BIGINT,
    timestamp TIMESTAMP
);

CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(255),
    market_id BIGINT,
    tx_hash VARCHAR(255),
    block_number BIGINT,
    payload VARCHAR(5000),
    timestamp TIMESTAMP
);

CREATE TABLE wallet_positions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wallet_address VARCHAR(255),
    market_id BIGINT,
    yes_position BOOLEAN,
    shares BIGINT,
    invested_amount BIGINT,
    current_value BIGINT,
    claimable_rewards BIGINT,
    claimed BOOLEAN DEFAULT FALSE
);

CREATE TABLE analytics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_markets BIGINT,
    total_volume BIGINT,
    total_traders BIGINT,
    resolved_markets BIGINT,
    open_interest BIGINT,
    bullish_percentage DOUBLE,
    bearish_percentage DOUBLE,
    snapshot_time TIMESTAMP
);