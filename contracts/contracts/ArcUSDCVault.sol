// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";
import "@openzeppelin/contracts/utils/Pausable.sol";

contract ArcUSDCVault is
    Ownable,
    ReentrancyGuard,
    Pausable
{
    //////////////////////////////////////////////////////
    // ARC TESTNET USDC
    //////////////////////////////////////////////////////

    address public constant ARC_USDC =
        0x3600000000000000000000000000000000000000;

    IERC20 public immutable usdc;

    //////////////////////////////////////////////////////
    // AUTHORIZED CONTRACTS
    //////////////////////////////////////////////////////

    address public treasury;

    //////////////////////////////////////////////////////
    // ACCOUNTING
    //////////////////////////////////////////////////////

    uint256 public totalDeposited;
    uint256 public totalWithdrawn;

    //////////////////////////////////////////////////////
    // EVENTS
    //////////////////////////////////////////////////////

    event TreasuryUpdated(
        address indexed treasury
    );

    event Deposit(
        address indexed user,
        uint256 amount
    );

    event Withdraw(
        address indexed user,
        uint256 amount
    );

    event TreasuryTransfer(
        address indexed treasury,
        uint256 amount
    );

    event TreasuryReceive(
        address indexed treasury,
        uint256 amount
    );

    //////////////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////////////

    constructor() Ownable(msg.sender) {
        usdc = IERC20(ARC_USDC);
    }

    //////////////////////////////////////////////////////
    // MODIFIERS
    //////////////////////////////////////////////////////

    modifier onlyTreasury() {
        require(
            msg.sender == treasury,
            "Not treasury"
        );
        _;
    }

    //////////////////////////////////////////////////////
    // ADMIN FUNCTIONS
    //////////////////////////////////////////////////////

    function setTreasury(
        address _treasury
    ) external onlyOwner {

        require(
            _treasury != address(0),
            "Invalid treasury"
        );

        treasury = _treasury;

        emit TreasuryUpdated(
            _treasury
        );
    }

    function pause()
        external
        onlyOwner
    {
        _pause();
    }

    function unpause()
        external
        onlyOwner
    {
        _unpause();
    }

    //////////////////////////////////////////////////////
    // USER FUNCTIONS
    //////////////////////////////////////////////////////

    function deposit(
        uint256 amount
    )
        external
        nonReentrant
        whenNotPaused
    {
        require(
            amount > 0,
            "Amount must be > 0"
        );

        bool success =
            usdc.transferFrom(
                msg.sender,
                address(this),
                amount
            );

        require(
            success,
            "Transfer failed"
        );

        totalDeposited += amount;

        emit Deposit(
            msg.sender,
            amount
        );
    }

    //////////////////////////////////////////////////////
    // OWNER WITHDRAW
    //////////////////////////////////////////////////////

    function withdraw(
        uint256 amount
    )
        external
        onlyOwner
        nonReentrant
    {
        require(
            amount > 0,
            "Invalid amount"
        );

        require(
            usdc.balanceOf(
                address(this)
            ) >= amount,
            "Insufficient balance"
        );

        totalWithdrawn += amount;

        bool success =
            usdc.transfer(
                msg.sender,
                amount
            );

        require(
            success,
            "Withdraw failed"
        );

        emit Withdraw(
            msg.sender,
            amount
        );
    }

    //////////////////////////////////////////////////////
    // TREASURY OPERATIONS
    //////////////////////////////////////////////////////

    function transferToTreasury(
        uint256 amount
    )
        external
        onlyTreasury
        nonReentrant
    {
        require(
            amount > 0,
            "Invalid amount"
        );

        require(
            usdc.balanceOf(
                address(this)
            ) >= amount,
            "Insufficient vault balance"
        );

        bool success =
            usdc.transfer(
                treasury,
                amount
            );

        require(
            success,
            "Transfer failed"
        );

        emit TreasuryTransfer(
            treasury,
            amount
        );
    }

    function receiveFromTreasury(
        uint256 amount
    )
        external
        onlyTreasury
        nonReentrant
    {
        require(
            amount > 0,
            "Invalid amount"
        );

        bool success =
            usdc.transferFrom(
                treasury,
                address(this),
                amount
            );

        require(
            success,
            "Transfer failed"
        );

        emit TreasuryReceive(
            treasury,
            amount
        );
    }

    //////////////////////////////////////////////////////
    // VIEW FUNCTIONS
    //////////////////////////////////////////////////////

    function vaultBalance()
        external
        view
        returns (uint256)
    {
        return usdc.balanceOf(address(this));
    }

    function availableLiquidity()
        external
        view
        returns (uint256)
    {
        return usdc.balanceOf(address(this));
    }
}