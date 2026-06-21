import { ethers } from "hardhat";

async function main() {

  console.log("Deploying ArcPredict...");

  const Vault =
    await ethers.getContractFactory(
      "ArcUSDCVault"
    );

  const vault =
    await Vault.deploy();

  await vault.waitForDeployment();

  const vaultAddress =
    await vault.getAddress();

  console.log(
    "Vault:",
    vaultAddress
  );

  ////////////////////////////////////////////////////

  const Treasury =
    await ethers.getContractFactory(
      "MarketTreasury"
    );

  const treasury =
    await Treasury.deploy(
      vaultAddress
    );

  await treasury.waitForDeployment();

  const treasuryAddress =
    await treasury.getAddress();

  console.log(
    "Treasury:",
    treasuryAddress
  );

  ////////////////////////////////////////////////////

  const RewardDistributor =
    await ethers.getContractFactory(
      "RewardDistributor"
    );

  const rewardDistributor =
    await RewardDistributor.deploy(
      treasuryAddress
    );

  await rewardDistributor.waitForDeployment();

  const rewardAddress =
    await rewardDistributor.getAddress();

  console.log(
    "RewardDistributor:",
    rewardAddress
  );

  ////////////////////////////////////////////////////

  const Factory =
    await ethers.getContractFactory(
      "MarketFactory"
    );

  const factory =
    await Factory.deploy();

  await factory.waitForDeployment();

  const factoryAddress =
    await factory.getAddress();

  console.log(
    "Factory:",
    factoryAddress
  );

  ////////////////////////////////////////////////////

  const PredictionMarket =
    await ethers.getContractFactory(
      "PredictionMarket"
    );

  const predictionMarket =
    await PredictionMarket.deploy(
      factoryAddress,
      treasuryAddress,
      rewardAddress
    );

  await predictionMarket.waitForDeployment();

  const predictionMarketAddress =
    await predictionMarket.getAddress();

  console.log(
    "PredictionMarket:",
    predictionMarketAddress
  );

  ////////////////////////////////////////////////////
  // WIRE CONTRACTS
  ////////////////////////////////////////////////////

  await factory.setPredictionMarket(
    predictionMarketAddress
  );

  await treasury.setPredictionMarket(
    predictionMarketAddress
  );

  await treasury.setRewardDistributor(
    rewardAddress
  );

  await rewardDistributor.setPredictionMarket(
    predictionMarketAddress
  );

  await vault.setTreasury(
    treasuryAddress
  );

  console.log(
    "Contracts wired successfully"
  );
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});