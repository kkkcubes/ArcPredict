package io.arcpredict.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
    name = "trades",
    indexes = {

        @Index(
            name = "idx_trade_tx_hash",
            columnList = "txHash"
        ),

        @Index(
            name = "idx_trade_market_id",
            columnList = "marketId"
        ),

        @Index(
            name = "idx_trade_trader",
            columnList = "trader"
        )

    }
)

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
    nullable = false,
    unique = true
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