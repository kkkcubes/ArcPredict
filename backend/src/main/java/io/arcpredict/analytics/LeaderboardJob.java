package io.arcpredict.analytics;

import io.arcpredict.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaderboardJob {

    private static final Logger log =
        LoggerFactory.getLogger(
            LeaderboardJob.class
        );

    private final WalletRepository walletRepository;

    @Scheduled(fixedRate = 300000)
    public void execute() {

        long users =
            walletRepository.count();

        log.info(
            "Wallets: {}",
            users
        );

    }

}