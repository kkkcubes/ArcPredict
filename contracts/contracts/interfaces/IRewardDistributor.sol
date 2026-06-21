// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

interface IRewardDistributor {

    event RewardCalculated(
        address indexed trader,
        uint256 indexed marketId,
        uint256 reward
    );

    event RewardClaimed(
        address indexed trader,
        uint256 indexed marketId,
        uint256 reward
    );

    function calculateReward(
        address trader,
        uint256 marketId
    ) external view returns (uint256);

    function claimReward(
        uint256 marketId
    ) external;

    function hasClaimed(
        address trader,
        uint256 marketId
    ) external view returns (bool);
}