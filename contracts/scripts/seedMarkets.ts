import { ethers } from "hardhat";

async function main() {

  const marketAddress =
    process.env
      .PREDICTION_MARKET_ADDRESS!;

  const market =
    await ethers.getContractAt(
      "PredictionMarket",
      marketAddress
    );

  await market.createMarket(
    "Will BTC reach $150k before Dec 2026?",
    "Crypto",
    Math.floor(
      Date.now() / 1000
    ) + 86400 * 30
  );

  await market.createMarket(
    "Will ETH exceed $10k?",
    "Crypto",
    Math.floor(
      Date.now() / 1000
    ) + 86400 * 60
  );

  console.log(
    "Seed markets created"
  );
}

main().catch(console.error);