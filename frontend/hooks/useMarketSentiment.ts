"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  marketSentimentService,
} from "@/services/marketSentimentService";

export interface MarketSentiment {

  marketId: number;

  yesPool: number;

  noPool: number;

  totalPool: number;

  yesPercentage: number;

  noPercentage: number;

}

export function useMarketSentiment() {

  const [
    sentiment,
    setSentiment,
  ] = useState<
    MarketSentiment[]
  >([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const loadSentiment =
    async () => {

      try {

        const data =
  await marketSentimentService
    .getMarketSentiment();

        setSentiment(
          data
        );

      } finally {

        setLoading(
          false
        );

      }

    };

  useEffect(() => {

    loadSentiment();

  }, []);

  return {

    sentiment,

    loading,

    refresh:
      loadSentiment,

  };

}