// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

library MarketMath {

    uint256 internal constant PRECISION = 10000;

    function totalLiquidity(
        uint256 yesPool,
        uint256 noPool
    ) internal pure returns (uint256) {
        return yesPool + noPool;
    }

    function yesProbability(
        uint256 yesPool,
        uint256 noPool
    ) internal pure returns (uint256) {

        uint256 total = yesPool + noPool;

        if (total == 0) {
            return 5000;
        }

        return (yesPool * PRECISION) / total;
    }

    function noProbability(
        uint256 yesPool,
        uint256 noPool
    ) internal pure returns (uint256) {

        uint256 total = yesPool + noPool;

        if (total == 0) {
            return 5000;
        }

        return (noPool * PRECISION) / total;
    }

    function marketSentiment(
        uint256 yesPool,
        uint256 noPool
    ) internal pure returns (string memory) {

        if (yesPool > noPool) {
            return "Bullish";
        }

        if (noPool > yesPool) {
            return "Bearish";
        }

        return "Neutral";
    }

    function openInterest(
        uint256 yesPool,
        uint256 noPool
    ) internal pure returns (uint256) {
        return yesPool + noPool;
    }
}