CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(255),
    market_id BIGINT,
    tx_hash VARCHAR(255),
    block_number BIGINT,
    payload VARCHAR(5000),
    timestamp TIMESTAMP
);