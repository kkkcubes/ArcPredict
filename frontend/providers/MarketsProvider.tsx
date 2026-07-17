"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import {
  getMarkets,
} from "@/services/backendMarketService";

import {
  subscribe,
} from "@/lib/stomp";

interface MarketsContextType {
  markets: any[];
  loading: boolean;
  refresh: () => Promise<void>;
}

const MarketsContext =
  createContext<MarketsContextType | null>(
    null
  );

export function MarketsProvider({
  children,
}: {
  children: React.ReactNode;
}) {

  const [
    markets,
    setMarkets,
  ] = useState<any[]>([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const loadMarkets =
    async () => {

      try {

        const data =
          await getMarkets();

        console.log(
          "[MarketsProvider] API returned:",
          data
        );

        console.log(
          "[MarketsProvider] Count:",
          data?.length
        );

        setMarkets(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadMarkets();

  }, []);

  useEffect(() => {

    console.log(
      "[MarketsProvider] Current markets:",
      markets.length,
      markets
    );

  }, [markets]);

  useEffect(() => {

    const unsubscribe = subscribe(

      "/topic/markets",

      (message) => {

        const updatedMarket =
          JSON.parse(
            message.body
          );

        console.log(
          "[MarketsProvider] received",
          updatedMarket
        );

        setMarkets(
          previous => {

            const index =
              previous.findIndex(
                m =>
                  m.marketId ===
                  updatedMarket.marketId
              );

            if (index === -1) {

              return [
                updatedMarket,
                ...previous,
              ];

            }

            const copy = [...previous];

            copy[index] =
              updatedMarket;

            return copy;

          }

        );

      }

    );

    return () => {

      unsubscribe?.();

    };

  }, []);

  return (

    <MarketsContext.Provider
      value={{
        markets,
        loading,
        refresh:
          loadMarkets,
      }}
    >

      {children}

    </MarketsContext.Provider>

  );

}

export function useMarketsContext() {

  const context =
    useContext(
      MarketsContext
    );

  if (!context) {

    throw new Error(
      "useMarketsContext must be used inside MarketsProvider"
    );

  }

  return context;

}