package io.arcpredict.repository;

import io.arcpredict.entity.MarketEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository
        extends JpaRepository<
            MarketEntity,
            Long
        > {

    List<MarketEntity>
    findByResolved(
        Boolean resolved
    );

    List<MarketEntity>
    findByCategory(
        String category
    );

       @Query("""
        select coalesce(max(m.blockNumber), 0)
        from MarketEntity m
    """)
    Long findLatestBlock();

    @Query("""
        select coalesce(sum(m.totalVolume), 0)
        from MarketEntity m
    """)
    Long findTotalVolume();

    @Query("""
    select coalesce(sum(m.protocolFees), 0)
    from MarketEntity m
""")
Long findTotalProtocolFees();
}