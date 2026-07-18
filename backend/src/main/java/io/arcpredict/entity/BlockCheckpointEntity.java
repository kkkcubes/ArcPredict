package io.arcpredict.entity;

import java.math.BigInteger;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "block_checkpoint")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockCheckpointEntity {

    @Id
    private Long id;

    private BigInteger lastProcessedBlock;

    private Instant updatedAt;

}