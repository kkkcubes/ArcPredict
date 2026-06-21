"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  getMarkets,
} from "@/services/backendMarketService";

export function useBackendMarkets() {

  const [markets, setMarkets] =
    useState<any[]>([]);

  const [loading, setLoading] =
    useState(true);

  const loadMarkets =
    async () => {

      try {

        const data =
          await getMarkets();

        setMarkets(data);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {
    loadMarkets();
  }, []);

  return {
    markets,
    loading,
    refresh: loadMarkets,
    setMarkets,
  };
}