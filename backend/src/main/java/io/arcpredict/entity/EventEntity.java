package io.arcpredict.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;

@Entity
@Table(

    name = "events",

    indexes = {

        @Index(
            name = "idx_event_market_id",
            columnList = "marketId"
        ),

        @Index(
            name = "idx_event_event_type",
            columnList = "eventType"
        ),

        @Index(
            name = "idx_event_tx_hash",
            columnList = "txHash"
        ),

        @Index(
            name = "idx_event_timestamp",
            columnList = "timestamp"
        )

    }

)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    private Long marketId;

    private String txHash;

    private Long blockNumber;

    @Column(length = 5000)
    private String payload;

    private Instant timestamp;
}