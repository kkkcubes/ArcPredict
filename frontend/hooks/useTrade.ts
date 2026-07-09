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

import {
  registerTransactionToast,
} from "@/lib/transactionToastStore";  

export function useTrade() {

  const {
    writeContractAsync,
    isPending,
  } = useWriteContract();

  const buyYes = async (
    marketId: number,
    amount: number
  ) => {

    const toastId = toast.loading(
  "Transaction submitted..."
);

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

toast.loading(
  "Waiting for blockchain confirmation...",
  {
    id: toastId,
  }
);

registerTransactionToast(
  tx,
  toastId
);

return tx;

    } catch (error) {

      console.error(error);

      toast.error(
  "Transaction failed",
  {
    id: toastId,
  }
);

throw error;
    }
  };

  const buyNo = async (
    marketId: number,
    amount: number
  ) => {

    const toastId = toast.loading(
  "Transaction submitted..."
);

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

toast.loading(
  "Waiting for blockchain confirmation...",
  {
    id: toastId,
  }
);

registerTransactionToast(
  tx,
  toastId
);

return tx;

return tx;

    } catch (error) {

      console.error(error);

      toast.error(
  "Transaction failed",
  {
    id: toastId,
  }
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