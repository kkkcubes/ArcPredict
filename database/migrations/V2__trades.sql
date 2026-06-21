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