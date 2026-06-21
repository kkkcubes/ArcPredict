package io.arcpredict.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "trades")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
        nullable = false
    )
    private Long marketId;

    @Column(
        nullable = false
    )
    private String trader;

    @Column(
        nullable = false
    )
    private Boolean yesPosition;

    @Column(
        nullable = false
    )
    private Long amount;

    @Column(
        nullable = false
    )
    private String txHash;

    @Column(
        nullable = false
    )
    private Long blockNumber;

    @Column(
        nullable = false
    )
    private Instant timestamp;
}