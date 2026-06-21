// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";

interface IMarketFactory {
    function registerMarket()
        external
        returns (uint256);
}

interface IMarketTreasury {
    function depositLiquidity(
        uint256 marketId,
        uint256 amount
    ) external;

    function lockLiquidity(
        uint256 marketId,
        uint256 amount
    ) external;
}

interface IRewardDistributor {
    function assignReward(
        uint256 marketId,
        address trader,
        uint256 amount
    ) external;
}

contract PredictionMarket is
    Ownable,
    ReentrancyGuard
{
    //////////////////////////////////////////////////////
    // STRUCTS
    //////////////////////////////////////////////////////

    struct Market {
        uint256 id;
        string question;
        string category;

        address creator;

        uint256 createdAt;
        uint256 endTime;

        uint256 yesPool;
        uint256 noPool;

        uint256 totalVolume;
        uint256 participantCount;

        bool resolved;
        bool outcome;
    }

    //////////////////////////////////////////////////////
    // DEPENDENCIES
    //////////////////////////////////////////////////////

    IMarketFactory public factory;
    IMarketTreasury public treasury;
    IRewardDistributor public rewardDistributor;

    //////////////////////////////////////////////////////
    // STORAGE
    //////////////////////////////////////////////////////

    mapping(uint256 => Market)
        public markets;

    mapping(uint256 => mapping(address => uint256))
        public yesPositions;

    mapping(uint256 => mapping(address => uint256))
        public noPositions;

    //////////////////////////////////////////////////////
    // EVENTS
    //////////////////////////////////////////////////////

    event MarketCreated(
        uint256 indexed marketId,
        string question,
        address indexed creator
    );

    event SharesPurchased(
        address indexed trader,
        uint256 indexed marketId,
        bool side,
        uint256 amount
    );

    event MarketResolved(
        uint256 indexed marketId,
        bool outcome
    );

    //////////////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////////////

    constructor(
        address factoryAddress,
        address treasuryAddress,
        address rewardDistributorAddress
    )
        Ownable(msg.sender)
    {
        factory =
            IMarketFactory(
                factoryAddress
            );

        treasury =
            IMarketTreasury(
                treasuryAddress
            );

        rewardDistributor =
            IRewardDistributor(
                rewardDistributorAddress
            );
    }

    //////////////////////////////////////////////////////
    // CREATE MARKET
    //////////////////////////////////////////////////////

    function createMarket(
        string calldata question,
        string calldata category,
        uint256 endTime
    )
        external
        returns (uint256)
    {
        require(
            endTime >
                block.timestamp,
            "Invalid end time"
        );

        uint256 marketId =
            factory.registerMarket();

        Market storage m =
            markets[marketId];

        m.id = marketId;
        m.question = question;
        m.category = category;
        m.creator = msg.sender;
        m.createdAt =
            block.timestamp;
        m.endTime = endTime;

        emit MarketCreated(
            marketId,
            question,
            msg.sender
        );

        return marketId;
    }

    //////////////////////////////////////////////////////
    // BUY YES
    //////////////////////////////////////////////////////

    function buyYes(
        uint256 marketId,
        uint256 amount
    )
        external
        nonReentrant
    {
        Market storage m =
            markets[marketId];

        require(
            !m.resolved,
            "Resolved"
        );

        require(
            block.timestamp <
                m.endTime,
            "Market ended"
        );

        require(
            amount > 0,
            "Invalid amount"
        );

        yesPositions[
            marketId
        ][
            msg.sender
        ] += amount;

        m.yesPool += amount;
        m.totalVolume += amount;

        treasury.depositLiquidity(
            marketId,
            amount
        );

        emit SharesPurchased(
            msg.sender,
            marketId,
            true,
            amount
        );
    }

    //////////////////////////////////////////////////////
    // BUY NO
    //////////////////////////////////////////////////////

    function buyNo(
        uint256 marketId,
        uint256 amount
    )
        external
        nonReentrant
    {
        Market storage m =
            markets[marketId];

        require(
            !m.resolved,
            "Resolved"
        );

        require(
            block.timestamp <
                m.endTime,
            "Market ended"
        );

        require(
            amount > 0,
            "Invalid amount"
        );

        noPositions[
            marketId
        ][
            msg.sender
        ] += amount;

        m.noPool += amount;
        m.totalVolume += amount;

        treasury.depositLiquidity(
            marketId,
            amount
        );

        emit SharesPurchased(
            msg.sender,
            marketId,
            false,
            amount
        );
    }

    //////////////////////////////////////////////////////
    // RESOLVE MARKET
    //////////////////////////////////////////////////////

    function resolveMarket(
        uint256 marketId,
        bool outcome
    )
        external
        onlyOwner
    {
        Market storage m =
            markets[marketId];

        require(
            !m.resolved,
            "Already resolved"
        );

        require(
            block.timestamp >=
                m.endTime,
            "Market active"
        );

        m.resolved = true;
        m.outcome = outcome;

        emit MarketResolved(
            marketId,
            outcome
        );
    }

    //////////////////////////////////////////////////////
    // ASSIGN REWARD
    //////////////////////////////////////////////////////

    function assignReward(
        uint256 marketId,
        address trader,
        uint256 amount
    )
        external
        onlyOwner
    {
        rewardDistributor
            .assignReward(
                marketId,
                trader,
                amount
            );
    }

    //////////////////////////////////////////////////////
    // VIEWS
    //////////////////////////////////////////////////////

    function getMarket(
        uint256 marketId
    )
        external
        view
        returns (
            Market memory
        )
    {
        return markets[
            marketId
        ];
    }

    function getYesPosition(
        uint256 marketId,
        address trader
    )
        external
        view
        returns (uint256)
    {
        return yesPositions[
            marketId
        ][
            trader
        ];
    }

    function getNoPosition(
        uint256 marketId,
        address trader
    )
        external
        view
        returns (uint256)
    {
        return noPositions[
            marketId
        ][
            trader
        ];
    }
}