"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  portfolioService,
} from "@/services/portfolioService";

import {
  subscribe,
} from "@/lib/stomp";

export interface PortfolioTransaction {

  id: number;

  marketId: number;

  trader: string;

  yesPosition: boolean;

  amount: number;

  txHash: string;

  blockNumber: number;

  timestamp: string;

}

interface TransactionPage {

  content: PortfolioTransaction[];

  totalElements: number;

  totalPages: number;

  number: number;

  size: number;

}

export function usePortfolioTransactions(

  wallet: string,

  page = 0,

  size = 20

) {

  const [
    transactions,
    setTransactions,
  ] =
    useState<PortfolioTransaction[]>([]);

  const [
    totalPages,
    setTotalPages,
  ] =
    useState(0);

  const [
    totalElements,
    setTotalElements,
  ] =
    useState(0);

  const [
    loading,
    setLoading,
  ] =
    useState(true);

  const [
    error,
    setError,
  ] =
    useState<string | null>(
      null
    );

  useEffect(() => {

    if (!wallet) {

      setLoading(false);

      return;

    }

    const load = async () => {

      try {

        setLoading(true);

        const response: TransactionPage =

          await portfolioService
            .getTransactionHistory(

              wallet,

              page,

              size

            );

        setTransactions(
          response.content
        );

        setTotalPages(
          response.totalPages
        );

        setTotalElements(
          response.totalElements
        );

        setError(
          null
        );

      } catch {

        setError(
          "Failed to load transaction history"
        );

      } finally {

        setLoading(
          false
        );

      }

    };

    load();

    const unsubscribe =
  subscribe(

    "/topic/trades",

    () => {

      load();

    }

  );

  }, [
    wallet,
    page,
    size,
  ]);

  return {

    transactions,

    totalPages,

    totalElements,

    loading,

    error,

  };

}