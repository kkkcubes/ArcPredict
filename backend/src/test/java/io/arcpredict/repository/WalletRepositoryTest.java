package io.arcpredict.repository;

import io.arcpredict.entity.WalletPositionEntity;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    void shouldFindWalletByAddress() {

        WalletPositionEntity position1 =
            WalletPositionEntity.builder()
                .walletAddress("0xwallet")
                .marketId(1L)
                .yesPosition(true)
                .shares(100L)
                .claimed(false)
                .build();

        WalletPositionEntity position2 =
            WalletPositionEntity.builder()
                .walletAddress("0xwallet")
                .marketId(2L)
                .yesPosition(false)
                .shares(200L)
                .claimed(false)
                .build();

        WalletPositionEntity position3 =
            WalletPositionEntity.builder()
                .walletAddress("0xother")
                .marketId(3L)
                .yesPosition(true)
                .shares(300L)
                .claimed(false)
                .build();

        walletRepository.save(position1);
        walletRepository.save(position2);
        walletRepository.save(position3);

        assertEquals(
            2,
            walletRepository
                .findByWalletAddress("0xwallet")
                .size()
        );

    }

    @Test
void shouldFindWalletsByMarketId() {

    WalletPositionEntity position1 =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet1")
            .marketId(100L)
            .yesPosition(true)
            .shares(100L)
            .claimed(false)
            .build();

    WalletPositionEntity position2 =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet2")
            .marketId(100L)
            .yesPosition(false)
            .shares(200L)
            .claimed(false)
            .build();

    WalletPositionEntity position3 =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet3")
            .marketId(200L)
            .yesPosition(true)
            .shares(300L)
            .claimed(false)
            .build();

    walletRepository.save(position1);
    walletRepository.save(position2);
    walletRepository.save(position3);

    assertEquals(
        2,
        walletRepository
            .findByMarketId(100L)
            .size()
    );

    assertEquals(
        1,
        walletRepository
            .findByMarketId(200L)
            .size()
    );

}

@Test
void shouldFindClaimedWallets() {

    WalletPositionEntity claimed =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet1")
            .marketId(1L)
            .yesPosition(true)
            .shares(100L)
            .claimed(true)
            .build();

    WalletPositionEntity unclaimed =
        WalletPositionEntity.builder()
            .walletAddress("0xwallet2")
            .marketId(2L)
            .yesPosition(false)
            .shares(200L)
            .claimed(false)
            .build();

    walletRepository.save(claimed);
    walletRepository.save(unclaimed);

    assertEquals(
        1,
        walletRepository
            .findByClaimed(true)
            .size()
    );

    assertEquals(
        1,
        walletRepository
            .findByClaimed(false)
            .size()
    );

}

@Test
void shouldFindWalletByAddressMarketAndPosition() {

    WalletPositionEntity yesPosition =

        WalletPositionEntity.builder()
            .walletAddress("0xwallet")
            .marketId(1L)
            .yesPosition(true)
            .shares(100L)
            .claimed(false)
            .build();

    WalletPositionEntity noPosition =

        WalletPositionEntity.builder()
            .walletAddress("0xwallet")
            .marketId(1L)
            .yesPosition(false)
            .shares(50L)
            .claimed(false)
            .build();

    walletRepository.save(yesPosition);
    walletRepository.save(noPosition);

    Optional<WalletPositionEntity> result =

        walletRepository
            .findByWalletAddressAndMarketIdAndYesPosition(
                "0xwallet",
                1L,
                true
            );

    assertTrue(
        result.isPresent()
    );

    assertEquals(
        100L,
        result
            .get()
            .getShares()
    );

}

@Test
void shouldFindWalletByAddressAndMarketId() {

    WalletPositionEntity position =

        WalletPositionEntity.builder()
            .walletAddress("0xwallet")
            .marketId(5L)
            .yesPosition(true)
            .shares(250L)
            .claimed(false)
            .build();

    walletRepository.save(position);

    Optional<WalletPositionEntity> result =

        walletRepository
            .findByWalletAddressAndMarketId(
                "0xwallet",
                5L
            );

    assertTrue(
        result.isPresent()
    );

    assertEquals(
        250L,
        result
            .get()
            .getShares()
    );

    assertEquals(
        true,
        result
            .get()
            .getYesPosition()
    );

}

}