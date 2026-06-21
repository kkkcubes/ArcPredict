package io.arcpredict.service;

import io.arcpredict.entity.WalletPositionEntity;
import io.arcpredict.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final WalletRepository
        walletRepository;

    public List<WalletPositionEntity>
    getPortfolio(
        String wallet
    ) {

        return walletRepository
            .findByWalletAddress(
                wallet
            );
    }

    public WalletPositionEntity
    savePosition(
        WalletPositionEntity entity
    ) {

        return walletRepository
            .save(entity);
    }
}