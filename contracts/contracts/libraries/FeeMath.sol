// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

library FeeMath {

    uint256 internal constant BASIS_POINTS = 10000;

    function calculateTradingFee(
        uint256 amount,
        uint256 feeBps
    ) internal pure returns (uint256) {

        return
            (amount * feeBps)
            / BASIS_POINTS;
    }

    function calculateSettlementFee(
        uint256 reward,
        uint256 feeBps
    ) internal pure returns (uint256) {

        return
            (reward * feeBps)
            / BASIS_POINTS;
    }

    function calculateMarketCreationFee(
        uint256 fee
    ) internal pure returns (uint256) {

        return fee;
    }

    function amountAfterFee(
        uint256 amount,
        uint256 fee
    ) internal pure returns (uint256) {

        return amount - fee;
    }
}