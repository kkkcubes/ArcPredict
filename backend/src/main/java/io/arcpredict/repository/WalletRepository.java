package io.arcpredict.repository;

import io.arcpredict.entity.WalletPositionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository
        extends JpaRepository<
            WalletPositionEntity,
            Long
        > {

    List<WalletPositionEntity>
    findByWalletAddress(
        String walletAddress
    );

    List<WalletPositionEntity>
    findByMarketId(
        Long marketId
    );

    List<WalletPositionEntity>
    findByClaimed(
        Boolean claimed
    );

    Optional<WalletPositionEntity>
    findByWalletAddressAndMarketIdAndYesPosition(
        String walletAddress,
        Long marketId,
        Boolean yesPosition
    );

    Optional<WalletPositionEntity>
findByWalletAddressAndMarketId(
    String walletAddress,
    Long marketId
);

}