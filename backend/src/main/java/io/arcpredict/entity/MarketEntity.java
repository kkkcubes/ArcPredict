package io.arcpredict.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "markets")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketEntity {

    @Id
    private Long marketId;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = false)
    private Long endTime;

    @Column(nullable = false)
    private Long yesPool;

    @Column(nullable = false)
    private Long noPool;

    @Column(nullable = false)
    private Boolean resolved;

    private Boolean outcome;

    private Long totalVolume;

    private Long participants;

    private Instant createdAt;
}