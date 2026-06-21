// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

interface IMarketFactory {

    event MarketCreated(
        uint256 indexed marketId,
        address indexed marketContract
    );

    function registerMarket(
        uint256 marketId
    ) external;

    function getAllMarkets()
        external
        view
        returns (uint256[] memory);

    function marketExists(
        uint256 marketId
    ) external view returns (bool);
}