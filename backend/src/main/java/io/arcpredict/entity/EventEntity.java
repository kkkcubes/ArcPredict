package io.arcpredict.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "events")

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