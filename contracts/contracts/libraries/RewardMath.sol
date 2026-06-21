// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

library RewardMath {

    function calculateWinnerReward(
        uint256 userStake,
        uint256 winningPool,
        uint256 totalPool
    ) internal pure returns (uint256) {

        if (winningPool == 0) {
            return 0;
        }

        return
            (userStake * totalPool)
            / winningPool;
    }

    function calculateProfit(
        uint256 reward,
        uint256 originalStake
    ) internal pure returns (uint256) {

        if (reward <= originalStake) {
            return 0;
        }

        return reward - originalStake;
    }

    function rewardPercentage(
        uint256 reward,
        uint256 stake
    ) internal pure returns (uint256) {

        if (stake == 0) {
            return 0;
        }

        return (reward * 10000) / stake;
    }
}