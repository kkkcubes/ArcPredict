package io.arcpredict.analytics;

import io.arcpredict.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaderboardJob {

    private final WalletRepository walletRepository;

    @Scheduled(fixedRate = 300000)
    public void execute() {

        long users =
                walletRepository.count();

        System.out.println(
                "[LEADERBOARD JOB] Wallets: "
                        + users
        );
    }
}