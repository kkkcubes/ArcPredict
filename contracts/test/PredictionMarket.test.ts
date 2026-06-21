import { expect } from "chai";
import { ethers } from "hardhat";

describe("PredictionMarket", function () {

  let predictionMarket: any;
  let factory: any;
  let treasury: any;
  let rewardDistributor: any;

  beforeEach(async () => {

    const Factory =
      await ethers.getContractFactory("MarketFactory");

    factory = await Factory.deploy();

    const Vault =
      await ethers.getContractFactory(
        "ArcUSDCVault"
      );

    const vault =
      await Vault.deploy();

    await vault.waitForDeployment();

    const Treasury =
      await ethers.getContractFactory(
        "MarketTreasury"
      );

    treasury =
      await Treasury.deploy(
        await vault.getAddress()
      );

    const Reward =
      await ethers.getContractFactory(
        "RewardDistributor"
      );

    rewardDistributor =
      await Reward.deploy(
        treasury.target
      );

    const Prediction =
      await ethers.getContractFactory(
        "PredictionMarket"
      );

    predictionMarket =
      await Prediction.deploy(
        factory.target,
        treasury.target,
        rewardDistributor.target
      );

    await factory.setPredictionMarket(
      predictionMarket.target
    );
  });

  it("should create market", async () => {

    await predictionMarket.createMarket(
      "BTC > 150k?",
      "Crypto",
      Math.floor(Date.now() / 1000) + 86400
    );

    const market =
      await predictionMarket.getMarket(1);

    expect(market.id).to.equal(1);
  });

});