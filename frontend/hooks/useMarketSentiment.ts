"use client";

import {
  useEffect,
  useState,
} from "react";

const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

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

        const response =
          await fetch(

            `${API_URL}/api/markets/sentiment`,

            {
              cache: "no-store",
            }

          );

        if (!response.ok) {

          throw new Error(
            "Failed to fetch market sentiment"
          );

        }

        const data =
          await response.json();

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