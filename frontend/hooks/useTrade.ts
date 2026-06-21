"use client";

import {
  useWriteContract
} from "wagmi";

import {
  toast,
} from "react-hot-toast";

import PredictionMarketABI
  from "@/abi/PredictionMarket.json";

import { CONTRACTS }
  from "@/lib/contracts";

export function useTrade() {

  const {
    writeContractAsync,
    isPending,
  } = useWriteContract();

  const buyYes = async (
    marketId: number,
    amount: number
  ) => {

    try {

      const tx =
        await writeContractAsync({
          address:
            CONTRACTS.predictionMarket as `0x${string}`,
          abi:
            PredictionMarketABI.abi,
          functionName:
            "buyYes",
          args: [
            BigInt(marketId),
            BigInt(amount),
          ],
        });

      toast.success(
        "YES position submitted"
      );

      return tx;

    } catch (error) {

      console.error(error);

      toast.error(
        "Transaction failed"
      );

      throw error;
    }
  };

  const buyNo = async (
    marketId: number,
    amount: number
  ) => {

    try {

      const tx =
        await writeContractAsync({
          address:
            CONTRACTS.predictionMarket as `0x${string}`,
          abi:
            PredictionMarketABI.abi,
          functionName:
            "buyNo",
          args: [
            BigInt(marketId),
            BigInt(amount),
          ],
        });

      toast.success(
        "NO position submitted"
      );

      return tx;

    } catch (error) {

      console.error(error);

      toast.error(
        "Transaction failed"
      );

      throw error;
    }
  };

  return {
    buyYes,
    buyNo,
    isPending,
  };
}