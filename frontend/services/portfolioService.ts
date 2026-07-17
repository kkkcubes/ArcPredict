import { readContract } from "@wagmi/core";

import { wagmiConfig } from "@/lib/wagmi";
import { CONTRACTS } from "@/lib/contracts";

import RewardDistributorArtifact
  from "@/abi/RewardDistributor.json";

const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

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

   async getPortfolioAnalytics(
    wallet: string
  ) {

    const response =
      await fetch(
        `${API_URL}/api/portfolio/analytics/${wallet}`,
        {
          cache: "no-store",
        }
      );

    if (
      !response.ok
    ) {

      throw new Error(
        "Failed to fetch portfolio analytics"
      );

    }

    return response.json();

  },

  async getWalletPositions(
  wallet: string
) {

  const response =
    await fetch(

      `${API_URL}/api/portfolio/positions/${wallet}`,

      {
        cache: "no-store",
      }

    );

  if (!response.ok) {

    throw new Error(
      "Failed to fetch wallet positions"
    );

  }

  return response.json();

},

  async getTransactionHistory(

    wallet: string,

    page = 0,

    size = 20

  ) {

    const response =
      await fetch(

        `${API_URL}/api/portfolio/transactions/${wallet}?page=${page}&size=${size}`,

        {
          cache: "no-store",
        }

      );

    if (
      !response.ok
    ) {

      throw new Error(
        "Failed to fetch transaction history"
      );

    }

    return response.json();

  },

};