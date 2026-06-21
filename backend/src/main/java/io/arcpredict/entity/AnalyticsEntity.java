package io.arcpredict.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "analytics")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalMarkets;

    private Long totalVolume;

    private Long totalTraders;

    private Long resolvedMarkets;

    private Long openInterest;

    private Double bullishPercentage;

    private Double bearishPercentage;

    private Instant snapshotTime;
}