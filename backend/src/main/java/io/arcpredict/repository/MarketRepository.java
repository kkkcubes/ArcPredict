package io.arcpredict.repository;

import io.arcpredict.entity.MarketEntity;

import org.springframework.data.jpa.repository.JpaRepository;
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
}