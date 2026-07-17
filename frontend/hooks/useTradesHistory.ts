"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  getTrades,
  getTradesByWallet,
} from "@/services/tradeService";

export interface Trade {

  id: number;

  marketId: number;

  trader: string;

  yesPosition: boolean;

  amount: number;

  txHash: string;

  blockNumber: number;

  timestamp: string;

}

export function useTradesHistory(
  wallet?: string
) {

  const [
    trades,
    setTrades,
  ] = useState<Trade[]>([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const [
    error,
    setError,
  ] = useState<string | null>(
    null
  );

  useEffect(() => {

    async function loadTrades() {

      try {

        setLoading(
          true
        );

        if (wallet) {

          const response =
            await getTradesByWallet(
              wallet
            );

          setTrades(
            response.content ?? []
          );

        } else {

          const data =
            await getTrades();

          setTrades(
            data
          );

        }

        setError(
          null
        );

      } catch {

        setError(
          "Failed to load trades."
        );

      } finally {

        setLoading(
          false
        );

      }

    }

    loadTrades();

  }, [
    wallet,
  ]);

  return {

    trades,

    loading,

    error,

  };

}