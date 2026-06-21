// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

interface IPredictionMarket {

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

    event SharesSold(
        address indexed trader,
        uint256 indexed marketId,
        bool side,
        uint256 amount
    );

    event MarketResolved(
        uint256 indexed marketId,
        bool outcome
    );

    event RewardsClaimed(
        address indexed trader,
        uint256 indexed marketId,
        uint256 amount
    );

    function createMarket(
        string calldata question,
        string calldata category,
        uint256 endTime
    ) external returns (uint256);

    function buyYes(
        uint256 marketId,
        uint256 amount
    ) external;

    function buyNo(
        uint256 marketId,
        uint256 amount
    ) external;

    function sellYes(
        uint256 marketId,
        uint256 amount
    ) external;

    function sellNo(
        uint256 marketId,
        uint256 amount
    ) external;

    function resolveMarket(
        uint256 marketId,
        bool outcome
    ) external;

    function claimRewards(
        uint256 marketId
    ) external;

    function getMarket(
        uint256 marketId
    ) external view returns (Market memory);

    function getMarketCount()
        external
        view
        returns (uint256);
}