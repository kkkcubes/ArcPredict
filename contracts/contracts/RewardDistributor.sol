// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";

interface IMarketTreasury {

    function releaseLiquidity(
        uint256 marketId,
        uint256 amount
    ) external;

    function getMarketLiquidity(
        uint256 marketId
    )
        external
        view
        returns (uint256);
}

contract RewardDistributor is
    Ownable,
    ReentrancyGuard
{
    //////////////////////////////////////////////////////
    // TREASURY
    //////////////////////////////////////////////////////

    IMarketTreasury public treasury;

    //////////////////////////////////////////////////////
    // AUTHORIZED CONTRACT
    //////////////////////////////////////////////////////

    address public predictionMarket;

    //////////////////////////////////////////////////////
    // CLAIM TRACKING
    //////////////////////////////////////////////////////

    mapping(uint256 => mapping(address => bool))
        public hasClaimed;

    mapping(uint256 => mapping(address => uint256))
        public rewards;

    //////////////////////////////////////////////////////
    // EVENTS
    //////////////////////////////////////////////////////

    event PredictionMarketUpdated(
        address indexed contractAddress
    );

    event RewardAssigned(
        uint256 indexed marketId,
        address indexed trader,
        uint256 amount
    );

    event RewardClaimed(
        uint256 indexed marketId,
        address indexed trader,
        uint256 amount
    );

    //////////////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////////////

    constructor(
        address treasuryAddress
    )
        Ownable(msg.sender)
    {
        treasury =
            IMarketTreasury(
                treasuryAddress
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

    //////////////////////////////////////////////////////
    // REWARD LOGIC
    //////////////////////////////////////////////////////

    function assignReward(
        uint256 marketId,
        address trader,
        uint256 amount
    )
        external
        onlyPredictionMarket
    {
        require(
            trader != address(0),
            "Invalid trader"
        );

        require(
            amount > 0,
            "Invalid amount"
        );

        rewards[
            marketId
        ][
            trader
        ] += amount;

        emit RewardAssigned(
            marketId,
            trader,
            amount
        );
    }

    function claimReward(
        uint256 marketId
    )
        external
        nonReentrant
    {
        require(
            !hasClaimed[
                marketId
            ][
                msg.sender
            ],
            "Already claimed"
        );

        uint256 reward =
            rewards[
                marketId
            ][
                msg.sender
            ];

        require(
            reward > 0,
            "No reward"
        );

        hasClaimed[
            marketId
        ][
            msg.sender
        ] = true;

        treasury.releaseLiquidity(
            marketId,
            reward
        );

        emit RewardClaimed(
            marketId,
            msg.sender,
            reward
        );
    }

    //////////////////////////////////////////////////////
    // VIEWS
    //////////////////////////////////////////////////////

    function getReward(
        uint256 marketId,
        address trader
    )
        external
        view
        returns (uint256)
    {
        return
            rewards[
                marketId
            ][
                trader
            ];
    }

    function claimed(
        uint256 marketId,
        address trader
    )
        external
        view
        returns (bool)
    {
        return
            hasClaimed[
                marketId
            ][
                trader
            ];
    }
}