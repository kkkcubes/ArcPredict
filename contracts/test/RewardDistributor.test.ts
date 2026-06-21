import { expect } from "chai";
import { ethers } from "hardhat";

describe(
  "RewardDistributor",
  function () {

    let reward: any;

    beforeEach(async () => {

      const Reward =
        await ethers.getContractFactory(
          "RewardDistributor"
        );

      reward =
        await Reward.deploy(
          ethers.ZeroAddress
        );
    });

    it(
      "should deploy",
      async () => {

        expect(
          reward.target
        ).to.not.equal(
          ethers.ZeroAddress
        );
    });

});