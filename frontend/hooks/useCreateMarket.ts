"use client";

import { useWriteContract } from "wagmi";

import PredictionMarketABI
  from "@/abi/PredictionMarket.json";

import { CONTRACTS }
  from "@/lib/contracts";

export function useCreateMarket() {

  const {
    writeContractAsync,
    isPending,
  } = useWriteContract();

  const createMarket = async (
    question: string,
    category: string,
    endTime: number
  ) => {

    console.log(
      "createMarket() called"
    );

    try {

      const hash =
        await writeContractAsync({

          address:
            CONTRACTS.predictionMarket as `0x${string}`,

          abi:
            PredictionMarketABI.abi,

          functionName:
            "createMarket",

          args: [
            question,
            category,
            BigInt(endTime),
          ],
        });

      console.log(
        "Create Market TX:",
        hash
      );

      return hash;

    } catch (error) {

      console.error(
        "Create Market Error:",
        error
      );

      throw error;

    }

  };

  return {
    createMarket,
    isPending,
  };

}