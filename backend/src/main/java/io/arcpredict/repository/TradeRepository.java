package io.arcpredict.repository;

import io.arcpredict.entity.TradeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository
        extends JpaRepository<
            TradeEntity,
            Long
        > {

    List<TradeEntity>
findByMarketId(
    Long marketId
);

List<TradeEntity>
findByTrader(
    String trader
);

List<TradeEntity>
findByTraderOrderByTimestampDesc(
    String trader
);

Page<TradeEntity>
findByTraderOrderByTimestampDesc(

    String trader,

    Pageable pageable

);

    Optional<TradeEntity>
    findByTxHash(
        String txHash
    );

    List<TradeEntity>
    findByTxHashIn(
        List<String> txHashes
    );

    long countByMarketId(
        Long marketId
    );

    @Query(
        """
        select count(distinct t.trader)
        from TradeEntity t
        where t.marketId = :marketId
        """
    )
    long countDistinctTraderByMarketId(

        @Param("marketId")
        Long marketId

    );

    Page<TradeEntity>
    findAll(

        Pageable pageable

    );

}