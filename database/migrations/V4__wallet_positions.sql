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