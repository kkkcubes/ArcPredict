import { expect } from "chai";
import { ethers } from "hardhat";

describe(
  "ArcPredict Integration",
  function () {

    it(
      "contracts deploy together",
      async () => {

        const Vault =
          await ethers.getContractFactory(
            "ArcUSDCVault"
          );

        const vault =
          await Vault.deploy();

        const Treasury =
          await ethers.getContractFactory(
            "MarketTreasury"
          );

        const treasury =
          await Treasury.deploy(
            vault.target
          );

        const Reward =
          await ethers.getContractFactory(
            "RewardDistributor"
          );

        const reward =
          await Reward.deploy(
            treasury.target
          );

        const Factory =
          await ethers.getContractFactory(
            "MarketFactory"
          );

        const factory =
          await Factory.deploy();

        expect(
          vault.target
        ).to.not.equal(
          ethers.ZeroAddress
        );

        expect(
          treasury.target
        ).to.not.equal(
          ethers.ZeroAddress
        );

        expect(
          reward.target
        ).to.not.equal(
          ethers.ZeroAddress
        );

        expect(
          factory.target
        ).to.not.equal(
          ethers.ZeroAddress
        );
    });

});