package io.arcpredict.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "wallet_positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletPositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String walletAddress;

    private Long marketId;

    private Boolean yesPosition;

    private Long shares;

    private Long investedAmount;

    private Long currentValue;

    private Long claimableRewards;

    private Boolean claimed;

    private Boolean winner;

    private Boolean settled;

    private Long claimedAmount;

    private Instant claimedAt;

}