package io.arcpredict.repository;

import io.arcpredict.entity.MarketEntity;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MarketRepositoryTest {

    @Autowired
    private MarketRepository marketRepository;

    @Test
    void shouldFindLatestBlock() {

        MarketEntity market1 =
            MarketEntity.builder()
                .marketId(1L)
                .question("BTC 200K?")
                .category("Crypto")
                .creator("0x111")
                .endTime(1000L)
                .yesPool(100L)
                .noPool(50L)
                .resolved(false)
                .totalVolume(150L)
                .protocolFees(10L)
                .participants(1L)
                .blockNumber(100L)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        MarketEntity market2 =
            MarketEntity.builder()
                .marketId(2L)
                .question("ETH 10K?")
                .category("Crypto")
                .creator("0x222")
                .endTime(2000L)
                .yesPool(200L)
                .noPool(100L)
                .resolved(true)
                .totalVolume(300L)
                .protocolFees(20L)
                .participants(2L)
                .blockNumber(250L)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        marketRepository.save(market1);
        marketRepository.save(market2);

        Long latestBlock =
            marketRepository.findLatestBlock();

        assertEquals(
            250L,
            latestBlock
        );

    }

}