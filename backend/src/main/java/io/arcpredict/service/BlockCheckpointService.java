package io.arcpredict.service;

import java.math.BigInteger;
import java.time.Instant;

import io.arcpredict.entity.BlockCheckpointEntity;
import io.arcpredict.repository.BlockCheckpointRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockCheckpointService {

    private static final Long CHECKPOINT_ID = 1L;

    private final BlockCheckpointRepository
        blockCheckpointRepository;

    public BigInteger getLastProcessedBlock() {

        return blockCheckpointRepository
            .findById(CHECKPOINT_ID)
            .map(
                BlockCheckpointEntity::getLastProcessedBlock
            )
            .orElse(null);

    }

    public void updateLastProcessedBlock(
        BigInteger blockNumber
    ) {

        BlockCheckpointEntity checkpoint =
            blockCheckpointRepository
                .findById(CHECKPOINT_ID)
                .orElse(
                    BlockCheckpointEntity
                        .builder()
                        .id(CHECKPOINT_ID)
                        .build()
                );

        checkpoint.setLastProcessedBlock(
            blockNumber
        );

        checkpoint.setUpdatedAt(
            Instant.now()
        );

        blockCheckpointRepository.save(
            checkpoint
        );

    }

}