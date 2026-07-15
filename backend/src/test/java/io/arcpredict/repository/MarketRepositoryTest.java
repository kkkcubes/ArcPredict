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

    @Test
void shouldCalculateTotalVolume() {

    MarketEntity market1 =
        MarketEntity.builder()
            .marketId(10L)
            .question("BTC?")
            .category("Crypto")
            .creator("0xabc")
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
            .marketId(20L)
            .question("ETH?")
            .category("Crypto")
            .creator("0xdef")
            .endTime(2000L)
            .yesPool(200L)
            .noPool(100L)
            .resolved(true)
            .totalVolume(350L)
            .protocolFees(20L)
            .participants(2L)
            .blockNumber(200L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    marketRepository.save(market1);
    marketRepository.save(market2);

    assertEquals(
        500L,
        marketRepository.findTotalVolume()
    );

}

@Test
void shouldCalculateTotalProtocolFees() {

    MarketEntity market1 =
        MarketEntity.builder()
            .marketId(100L)
            .question("BTC")
            .category("Crypto")
            .creator("0x1")
            .endTime(1000L)
            .yesPool(100L)
            .noPool(50L)
            .resolved(false)
            .totalVolume(150L)
            .protocolFees(15L)
            .participants(1L)
            .blockNumber(100L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    MarketEntity market2 =
        MarketEntity.builder()
            .marketId(200L)
            .question("ETH")
            .category("Crypto")
            .creator("0x2")
            .endTime(2000L)
            .yesPool(200L)
            .noPool(100L)
            .resolved(true)
            .totalVolume(300L)
            .protocolFees(25L)
            .participants(2L)
            .blockNumber(200L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    marketRepository.save(market1);
    marketRepository.save(market2);

    assertEquals(
        40L,
        marketRepository.findTotalProtocolFees()
    );

}

@Test
void shouldCountResolvedMarkets() {

    MarketEntity market1 =
        MarketEntity.builder()
            .marketId(1000L)
            .question("BTC")
            .category("Crypto")
            .creator("0x1")
            .endTime(1000L)
            .yesPool(100L)
            .noPool(50L)
            .resolved(true)
            .totalVolume(150L)
            .protocolFees(15L)
            .participants(1L)
            .blockNumber(100L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    MarketEntity market2 =
        MarketEntity.builder()
            .marketId(2000L)
            .question("ETH")
            .category("Crypto")
            .creator("0x2")
            .endTime(2000L)
            .yesPool(200L)
            .noPool(100L)
            .resolved(false)
            .totalVolume(300L)
            .protocolFees(25L)
            .participants(2L)
            .blockNumber(200L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    MarketEntity market3 =
        MarketEntity.builder()
            .marketId(3000L)
            .question("SOL")
            .category("Crypto")
            .creator("0x3")
            .endTime(3000L)
            .yesPool(300L)
            .noPool(150L)
            .resolved(true)
            .totalVolume(450L)
            .protocolFees(30L)
            .participants(3L)
            .blockNumber(300L)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

    marketRepository.save(market1);
    marketRepository.save(market2);
    marketRepository.save(market3);

    assertEquals(
        2L,
        marketRepository.countByResolved(true)
    );

    assertEquals(
        1L,
        marketRepository.countByResolved(false)
    );

}

@Test
void shouldFindMarketsByCategory() {

    MarketEntity crypto =
        MarketEntity.builder()
            .marketId(4000L)
            .question("BTC")
            .category("Crypto")
            .creator("0x1")
            .endTime(1000L)
            .yesPool(100L)
            .noPool(50L)
            .resolved(false)
            .build();

    MarketEntity sports =
        MarketEntity.builder()
            .marketId(5000L)
            .question("World Cup")
            .category("Sports")
            .creator("0x2")
            .endTime(2000L)
            .yesPool(200L)
            .noPool(100L)
            .resolved(false)
            .build();

    marketRepository.save(crypto);
    marketRepository.save(sports);

    assertEquals(
        1,
        marketRepository
            .findByCategory("Crypto")
            .size()
    );

    assertEquals(
        1,
        marketRepository
            .findByCategory("Sports")
            .size()
    );

}

@Test
void shouldFindResolvedMarkets() {

    MarketEntity resolved =
        MarketEntity.builder()
            .marketId(6000L)
            .question("BTC")
            .category("Crypto")
            .creator("0x1")
            .endTime(1000L)
            .yesPool(100L)
            .noPool(50L)
            .resolved(true)
            .build();

    MarketEntity active =
        MarketEntity.builder()
            .marketId(7000L)
            .question("ETH")
            .category("Crypto")
            .creator("0x2")
            .endTime(2000L)
            .yesPool(200L)
            .noPool(100L)
            .resolved(false)
            .build();

    marketRepository.save(resolved);
    marketRepository.save(active);

    assertEquals(
        1,
        marketRepository
            .findByResolved(true)
            .size()
    );

    assertEquals(
        1,
        marketRepository
            .findByResolved(false)
            .size()
    );

}

}