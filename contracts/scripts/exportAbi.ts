import fs from "fs";
import path from "path";

const contracts = [
  "ArcUSDCVault",
  "MarketTreasury",
  "RewardDistributor",
  "MarketFactory",
  "PredictionMarket"
];

for (const contract of contracts) {

  const artifactPath =
    path.join(
      __dirname,
      "..",
      "artifacts",
      "contracts",
      `${contract}.sol`,
      `${contract}.json`
    );

  const artifact =
    JSON.parse(
      fs.readFileSync(
        artifactPath,
        "utf8"
      )
    );

  const outputDir =
    path.join(
      __dirname,
      "..",
      "..",
      "frontend",
      "abi"
    );

  if (
    !fs.existsSync(outputDir)
  ) {
    fs.mkdirSync(
      outputDir,
      {
        recursive: true
      }
    );
  }

  fs.writeFileSync(
    path.join(
      outputDir,
      `${contract}.json`
    ),
    JSON.stringify(
      artifact.abi,
      null,
      2
    )
  );
}

console.log(
  "ABIs exported"
);