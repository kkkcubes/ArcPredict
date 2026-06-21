package io.arcpredict.repository;

import io.arcpredict.entity.TradeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}