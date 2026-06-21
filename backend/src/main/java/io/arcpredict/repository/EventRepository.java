package io.arcpredict.repository;

import io.arcpredict.entity.EventEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository
        extends JpaRepository<
            EventEntity,
            Long
        > {

    List<EventEntity>
    findByEventType(
        String eventType
    );

    List<EventEntity>
    findByMarketId(
        Long marketId
    );

    List<EventEntity>
    findTop50ByOrderByTimestampDesc();
}