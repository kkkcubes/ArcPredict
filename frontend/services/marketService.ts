import { readContract } from "@wagmi/core";

import { wagmiConfig } from "@/lib/wagmi";
import { CONTRACTS } from "@/lib/contracts";

import PredictionMarketArtifact
  from "@/abi/PredictionMarket.json";

export const marketService = {

  async getMarketCount() {
    return 1;
  },

  async getMarket(
    marketId: number
  ) {

    return await readContract(
      wagmiConfig,
      {
        address:
          CONTRACTS.predictionMarket as `0x${string}`,

        abi:
          PredictionMarketArtifact.abi,

        functionName:
          "getMarket",

        args: [marketId],
      }
    );
  },

  async getAllMarkets() {

    const count =
      Number(
        await this.getMarketCount()
      );

    const markets = [];

    for (
      let i = 1;
      i <= count;
      i++
    ) {

      markets.push(
        await this.getMarket(i)
      );
    }

    return markets;
  },
};