package io.arcpredict.repository;

import io.arcpredict.entity.BlockCheckpointEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockCheckpointRepository
    extends JpaRepository<
        BlockCheckpointEntity,
        Long
    > {

}