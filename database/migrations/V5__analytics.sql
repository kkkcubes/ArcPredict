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