package io.arcpredict.util;

import org.junit.jupiter.api.Test;

import org.web3j.crypto.Hash;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContractEventsTest {

    @Test
    void shouldContainCorrectMarketCreatedHash() {

        assertEquals(
            Hash.sha3String(
                "MarketCreated(uint256,string,address)"
            ),
            ContractEvents.MARKET_CREATED
        );

    }

    @Test
    void shouldContainCorrectSharesPurchasedHash() {

        assertEquals(
            Hash.sha3String(
                "SharesPurchased(address,uint256,bool,uint256)"
            ),
            ContractEvents.SHARES_PURCHASED
        );

    }

    @Test
    void shouldContainCorrectMarketResolvedHash() {

        assertEquals(
            Hash.sha3String(
                "MarketResolved(uint256,bool)"
            ),
            ContractEvents.MARKET_RESOLVED
        );

    }

    @Test
    void shouldContainCorrectRewardClaimedHash() {

        assertEquals(
            Hash.sha3String(
                "RewardClaimed(uint256,address,uint256)"
            ),
            ContractEvents.REWARD_CLAIMED
        );

    }

    @Test
    void shouldInstantiatePrivateConstructor() throws Exception {

        Constructor<ContractEvents> constructor =
            ContractEvents.class.getDeclaredConstructor();

        constructor.setAccessible(
            true
        );

        ContractEvents instance =
            constructor.newInstance();

        assertTrue(
            instance instanceof ContractEvents
        );

    }

}