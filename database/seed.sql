INSERT INTO markets (
    market_id,
    question,
    category,
    creator,
    end_time,
    yes_pool,
    no_pool,
    resolved,
    total_volume,
    participants,
    created_at
)
VALUES (
    1,
    'Will BTC exceed $150K in 2026?',
    'Crypto',
    '0xCreator',
    1790000000,
    5000,
    3000,
    FALSE,
    8000,
    12,
    CURRENT_TIMESTAMP
);

INSERT INTO analytics (
    total_markets,
    total_volume,
    total_traders,
    resolved_markets,
    open_interest,
    bullish_percentage,
    bearish_percentage,
    snapshot_time
)
VALUES (
    1,
    8000,
    12,
    0,
    8000,
    62.5,
    37.5,
    CURRENT_TIMESTAMP
);