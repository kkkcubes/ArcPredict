package io.arcpredict.util;

import org.web3j.crypto.Hash;

public final class ContractEvents {

    private ContractEvents() {}

    public static final String MARKET_CREATED =
        Hash.sha3String(
            "MarketCreated(uint256,string,address)"
        );

    public static final String SHARES_PURCHASED =
        Hash.sha3String(
            "SharesPurchased(address,uint256,bool,uint256)"
        );

    public static final String MARKET_RESOLVED =
        Hash.sha3String(
            "MarketResolved(uint256,bool)"
        );

        public static final String REWARD_CLAIMED =
    Hash.sha3String(
        "RewardClaimed(uint256,address,uint256)"
    );
}