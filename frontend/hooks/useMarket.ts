"use client";

import { useEffect, useState } from "react";
import { marketService } from "@/services/marketService";

export function useMarket(
  marketId: number
) {

  const [market, setMarket] =
    useState<any>(null);

  const [loading, setLoading] =
    useState(true);

  const loadMarket =
    async () => {

      try {

        const result =
          await marketService.getMarket(
            marketId
          );

        setMarket(result);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);
      }
    };

  useEffect(() => {

    if (marketId) {
      loadMarket();
    }

  }, [marketId]);

  return {
    market,
    loading,
    refresh: loadMarket,
  };
}