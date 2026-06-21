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

    return writeContractAsync({
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

  };

  return {
    createMarket,
    isPending,
  };
}