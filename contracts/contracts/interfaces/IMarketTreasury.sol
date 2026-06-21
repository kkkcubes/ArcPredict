// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

interface IMarketTreasury {

    event Deposit(
        address indexed user,
        uint256 amount
    );

    event Withdrawal(
        address indexed user,
        uint256 amount
    );

    event RewardPaid(
        address indexed user,
        uint256 amount
    );

    function deposit(
        uint256 amount
    ) external;

    function withdraw(
        uint256 amount
    ) external;

    function distributeReward(
        address user,
        uint256 amount
    ) external;

    function treasuryBalance()
        external
        view
        returns (uint256);
}