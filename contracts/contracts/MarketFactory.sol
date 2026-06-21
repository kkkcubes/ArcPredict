// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts/access/Ownable.sol";

contract MarketFactory is Ownable {

    //////////////////////////////////////////////////////
    // STATE
    //////////////////////////////////////////////////////

    uint256 public marketCount;

    address public predictionMarket;

    mapping(uint256 => bool)
        public marketExists;

    uint256[] private allMarkets;

    //////////////////////////////////////////////////////
    // EVENTS
    //////////////////////////////////////////////////////

    event PredictionMarketUpdated(
        address indexed predictionMarket
    );

    event MarketRegistered(
        uint256 indexed marketId,
        uint256 timestamp
    );

    //////////////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////////////

    constructor()
        Ownable(msg.sender)
    {}

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
        address _predictionMarket
    )
        external
        onlyOwner
    {
        require(
            _predictionMarket !=
                address(0),
            "Invalid address"
        );

        predictionMarket =
            _predictionMarket;

        emit PredictionMarketUpdated(
            _predictionMarket
        );
    }

    //////////////////////////////////////////////////////
    // MARKET REGISTRATION
    //////////////////////////////////////////////////////

    function registerMarket()
        external
        onlyPredictionMarket
        returns (uint256)
    {
        marketCount++;

        uint256 marketId =
            marketCount;

        marketExists[
            marketId
        ] = true;

        allMarkets.push(
            marketId
        );

        emit MarketRegistered(
            marketId,
            block.timestamp
        );

        return marketId;
    }

    //////////////////////////////////////////////////////
    // VIEWS
    //////////////////////////////////////////////////////

    function getAllMarkets()
        external
        view
        returns (
            uint256[] memory
        )
    {
        return allMarkets;
    }

    function getMarketCount()
        external
        view
        returns (uint256)
    {
        return marketCount;
    }

    function exists(
        uint256 marketId
    )
        external
        view
        returns (bool)
    {
        return marketExists[
            marketId
        ];
    }
}