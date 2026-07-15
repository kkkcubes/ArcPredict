package io.arcpredict.repository;

import io.arcpredict.entity.TradeEntity;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

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

    @Test
void shouldFindTradesByTrader() {

    TradeEntity trade1 =
        TradeEntity.builder()
            .marketId(1L)
            .trader("0xwallet")
            .yesPosition(true)
            .amount(100L)
            .txHash("0x111")
            .blockNumber(100L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade2 =
        TradeEntity.builder()
            .marketId(2L)
            .trader("0xwallet")
            .yesPosition(false)
            .amount(200L)
            .txHash("0x222")
            .blockNumber(200L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade3 =
        TradeEntity.builder()
            .marketId(3L)
            .trader("0xother")
            .yesPosition(true)
            .amount(300L)
            .txHash("0x333")
            .blockNumber(300L)
            .timestamp(
                Instant.now()
            )
            .build();

    tradeRepository.save(trade1);
    tradeRepository.save(trade2);
    tradeRepository.save(trade3);

    assertEquals(
        2,
        tradeRepository
            .findByTrader("0xwallet")
            .size()
    );

}

@Test
void shouldFindTradesByMarketId() {

    TradeEntity trade1 =
        TradeEntity.builder()
            .marketId(10L)
            .trader("0xwallet1")
            .yesPosition(true)
            .amount(100L)
            .txHash("0xaaa")
            .blockNumber(100L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade2 =
        TradeEntity.builder()
            .marketId(10L)
            .trader("0xwallet2")
            .yesPosition(false)
            .amount(200L)
            .txHash("0xbbb")
            .blockNumber(101L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade3 =
        TradeEntity.builder()
            .marketId(20L)
            .trader("0xwallet3")
            .yesPosition(true)
            .amount(300L)
            .txHash("0xccc")
            .blockNumber(102L)
            .timestamp(
                Instant.now()
            )
            .build();

    tradeRepository.save(trade1);
    tradeRepository.save(trade2);
    tradeRepository.save(trade3);

    assertEquals(
        2,
        tradeRepository
            .findByMarketId(10L)
            .size()
    );

    assertEquals(
        1,
        tradeRepository
            .findByMarketId(20L)
            .size()
    );

}

@Test
void shouldFindTradesByTxHashIn() {

    TradeEntity trade1 =
        TradeEntity.builder()
            .marketId(1L)
            .trader("0xwallet1")
            .yesPosition(true)
            .amount(100L)
            .txHash("0x111")
            .blockNumber(100L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade2 =
        TradeEntity.builder()
            .marketId(2L)
            .trader("0xwallet2")
            .yesPosition(false)
            .amount(200L)
            .txHash("0x222")
            .blockNumber(200L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade3 =
        TradeEntity.builder()
            .marketId(3L)
            .trader("0xwallet3")
            .yesPosition(true)
            .amount(300L)
            .txHash("0x333")
            .blockNumber(300L)
            .timestamp(
                Instant.now()
            )
            .build();

    tradeRepository.save(trade1);
    tradeRepository.save(trade2);
    tradeRepository.save(trade3);

    assertEquals(
        2,
        tradeRepository
            .findByTxHashIn(
                List.of(
                    "0x111",
                    "0x333"
                )
            )
            .size()
    );

}

@Test
void shouldCountTradesByMarketId() {

    TradeEntity trade1 =
        TradeEntity.builder()
            .marketId(100L)
            .trader("0xwallet1")
            .yesPosition(true)
            .amount(100L)
            .txHash("0xa1")
            .blockNumber(1L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade2 =
        TradeEntity.builder()
            .marketId(100L)
            .trader("0xwallet2")
            .yesPosition(false)
            .amount(200L)
            .txHash("0xa2")
            .blockNumber(2L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade3 =
        TradeEntity.builder()
            .marketId(200L)
            .trader("0xwallet3")
            .yesPosition(true)
            .amount(300L)
            .txHash("0xa3")
            .blockNumber(3L)
            .timestamp(
                Instant.now()
            )
            .build();

    tradeRepository.save(trade1);
    tradeRepository.save(trade2);
    tradeRepository.save(trade3);

    assertEquals(
        2L,
        tradeRepository.countByMarketId(100L)
    );

    assertEquals(
        1L,
        tradeRepository.countByMarketId(200L)
    );

}

@Test
void shouldCountDistinctTraderByMarketId() {

    TradeEntity trade1 =
        TradeEntity.builder()
            .marketId(500L)
            .trader("0xwallet1")
            .yesPosition(true)
            .amount(100L)
            .txHash("0xb1")
            .blockNumber(1L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade2 =
        TradeEntity.builder()
            .marketId(500L)
            .trader("0xwallet1")
            .yesPosition(false)
            .amount(200L)
            .txHash("0xb2")
            .blockNumber(2L)
            .timestamp(
                Instant.now()
            )
            .build();

    TradeEntity trade3 =
        TradeEntity.builder()
            .marketId(500L)
            .trader("0xwallet2")
            .yesPosition(true)
            .amount(300L)
            .txHash("0xb3")
            .blockNumber(3L)
            .timestamp(
                Instant.now()
            )
            .build();

    tradeRepository.save(trade1);
    tradeRepository.save(trade2);
    tradeRepository.save(trade3);

    assertEquals(
        2L,
        tradeRepository.countDistinctTraderByMarketId(
            500L
        )
    );

}

}