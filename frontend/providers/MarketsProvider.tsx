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
  stompClient,
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

    let subscription: any;

    const subscribe = () => {

      subscription =
        stompClient.subscribe(

          "/topic/markets",

          (message) => {

            const updatedMarket =
              JSON.parse(
                message.body
              );

            setMarkets(
              previous => {

                const index =
                  previous.findIndex(
                    m =>
                      m.marketId ===
                      updatedMarket.marketId
                  );

                if (
                  index === -1
                ) {

                  return [
                    updatedMarket,
                    ...previous,
                  ];

                }

                const copy =
                  [...previous];

                copy[index] =
                  updatedMarket;

                return copy;

              }
            );

          }

        );

    };

    if (
      stompClient.connected
    ) {

      subscribe();

    } else {

      const previous =
        stompClient.onConnect;

      stompClient.onConnect =
        frame => {

          previous?.(frame);

          subscribe();

        };

    }

    return () => {

      subscription?.unsubscribe();

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