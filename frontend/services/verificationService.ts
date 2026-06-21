import { publicClient }
  from "@/lib/rpc";

import { CONTRACTS }
  from "@/lib/contracts";

export const verificationService = {

  async getNetworkStatus() {

    const block =
      await publicClient
        .getBlockNumber();

    return {
      chainId:
        5042002,
      network:
        "Arc Testnet",
      latestBlock:
        Number(block),
    };
  },

  async getContracts() {

    return {
      predictionMarket:
        CONTRACTS.predictionMarket,

      marketFactory:
        CONTRACTS.marketFactory,

      rewardDistributor:
        CONTRACTS.rewardDistributor,

      marketTreasury:
        CONTRACTS.marketTreasury,

      vault:
        CONTRACTS.arcVault,
    };
  },
};