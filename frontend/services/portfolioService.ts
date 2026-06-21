import { readContract } from "@wagmi/core";

import { wagmiConfig } from "@/lib/wagmi";
import { CONTRACTS } from "@/lib/contracts";

import RewardDistributorArtifact
  from "@/abi/RewardDistributor.json";

export const portfolioService = {

  async getReward(
    marketId: number,
    wallet: string
  ) {

    return await readContract(
      wagmiConfig,
      {
        address:
          CONTRACTS.rewardDistributor as `0x${string}`,

        abi:
          RewardDistributorArtifact.abi as any,

        functionName:
          "getReward",

        args: [
          marketId,
          wallet,
        ],
      }
    );
  },

  async getClaimStatus(
    marketId: number,
    wallet: string
  ) {

    return await readContract(
      wagmiConfig,
      {
        address:
          CONTRACTS.rewardDistributor as `0x${string}`,

        abi:
          RewardDistributorArtifact.abi as any,

        functionName:
          "claimed",

        args: [
          marketId,
          wallet,
        ],
      }
    );
  },
};