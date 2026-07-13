package io.arcpredict.repository;

import io.arcpredict.entity.EventEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<EventEntity> findAll(
    Pageable pageable
);
}