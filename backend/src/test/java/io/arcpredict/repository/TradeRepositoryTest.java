package io.arcpredict.repository;

import io.arcpredict.entity.TradeEntity;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    void shouldFindTradeByTxHash() {

        TradeEntity trade =
            TradeEntity.builder()
                .marketId(1L)
                .trader("0xwallet")
                .yesPosition(true)
                .amount(100L)
                .txHash("0xabc")
                .blockNumber(12345L)
                .timestamp(
                    Instant.now()
                )
                .build();

        tradeRepository.save(trade);

        assertTrue(
            tradeRepository
                .findByTxHash("0xabc")
                .isPresent()
        );

        assertEquals(
            "0xwallet",
            tradeRepository
                .findByTxHash("0xabc")
                .get()
                .getTrader()
        );

    }

}