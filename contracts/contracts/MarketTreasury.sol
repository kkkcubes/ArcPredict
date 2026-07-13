// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";

interface IArcUSDCVault {
    function transferToTreasury(
        uint256 amount
    ) external;

    function receiveFromTreasury(
        uint256 amount
    ) external;

    function vaultBalance()
        external
        view
        returns (uint256);
}

contract MarketTreasury is
    Ownable,
    ReentrancyGuard
{
    //////////////////////////////////////////////////////
    // VAULT
    //////////////////////////////////////////////////////

    IArcUSDCVault public vault;

    //////////////////////////////////////////////////////
    // AUTHORIZED CONTRACTS
    //////////////////////////////////////////////////////

    address public predictionMarket;
    address public rewardDistributor;

    //////////////////////////////////////////////////////
    // ACCOUNTING
    //////////////////////////////////////////////////////

    uint256 public totalLiquidity;
    uint256 public totalLockedLiquidity;
    uint256 public totalReleasedLiquidity;

    //////////////////////////////////////////////////////
    // MARKET LIQUIDITY
    //////////////////////////////////////////////////////

    mapping(uint256 => uint256)
        public marketLiquidity;

    //////////////////////////////////////////////////////
    // EVENTS
    //////////////////////////////////////////////////////

    event PredictionMarketUpdated(
        address indexed contractAddress
    );

    event RewardDistributorUpdated(
        address indexed contractAddress
    );

    event LiquidityDeposited(
        uint256 indexed marketId,
        uint256 amount
    );

    event LiquidityLocked(
        uint256 indexed marketId,
        uint256 amount
    );

    event LiquidityReleased(
        uint256 indexed marketId,
        uint256 amount
    );

    //////////////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////////////

    constructor(
        address vaultAddress
    )
        Ownable(msg.sender)
    {
        require(
            vaultAddress != address(0),
            "Invalid vault"
        );

        vault =
            IArcUSDCVault(
                vaultAddress
            );
    }

    //////////////////////////////////////////////////////
    // MODIFIERS
    //////////////////////////////////////////////////////

    modifier onlyPredictionMarket() {
        require(
            msg.sender ==
                predictionMarket,
            "Not PredictionMarket"
        );
        _;
    }

    modifier onlyRewardDistributor() {
        require(
            msg.sender ==
                rewardDistributor,
            "Not RewardDistributor"
        );
        _;
    }

    //////////////////////////////////////////////////////
    // ADMIN
    //////////////////////////////////////////////////////

    function setPredictionMarket(
        address contractAddress
    )
        external
        onlyOwner
    {
        require(
            contractAddress !=
                address(0),
            "Invalid address"
        );

        predictionMarket =
            contractAddress;

        emit PredictionMarketUpdated(
            contractAddress
        );
    }

    function setRewardDistributor(
        address contractAddress
    )
        external
        onlyOwner
    {
        require(
            contractAddress !=
                address(0),
            "Invalid address"
        );

        rewardDistributor =
            contractAddress;

        emit RewardDistributorUpdated(
            contractAddress
        );
    }

    //////////////////////////////////////////////////////
    // LIQUIDITY MANAGEMENT
    //////////////////////////////////////////////////////

    function depositLiquidity(
        uint256 marketId,
        uint256 amount
    )
        external
        onlyPredictionMarket
        nonReentrant
    {
        require(
            amount > 0,
            "Invalid amount"
        );

        marketLiquidity[
            marketId
        ] += amount;

        totalLiquidity += amount;

        emit LiquidityDeposited(
            marketId,
            amount
        );
    }

    function lockLiquidity(
        uint256 marketId,
        uint256 amount
    )
        external
        onlyPredictionMarket
        nonReentrant
    {
        require(
            amount > 0,
            "Invalid amount"
        );

        require(
            marketLiquidity[
                marketId
            ] >= amount,
            "Insufficient liquidity"
        );

        totalLockedLiquidity +=
            amount;

        emit LiquidityLocked(
            marketId,
            amount
        );
    }

    function releaseLiquidity(
        uint256 marketId,
        uint256 amount
    )
        external
        onlyRewardDistributor
        nonReentrant
    {
        require(
            amount > 0,
            "Invalid amount"
        );

        require(
            marketLiquidity[
                marketId
            ] >= amount,
            "Insufficient liquidity"
        );

        marketLiquidity[
            marketId
        ] -= amount;

        totalReleasedLiquidity +=
            amount;

        emit LiquidityReleased(
            marketId,
            amount
        );
    }

    //////////////////////////////////////////////////////
    // VIEW FUNCTIONS
    //////////////////////////////////////////////////////

    function getMarketLiquidity(
        uint256 marketId
    )
        external
        view
        returns (uint256)
    {
        return
            marketLiquidity[
                marketId
            ];
    }

    function getVaultBalance()
        external
        view
        returns (uint256)
    {
        return
            vault.vaultBalance();
    }
}