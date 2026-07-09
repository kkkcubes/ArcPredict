"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  getMarkets,
} from "@/services/backendMarketService";

import {
  stompClient,
} from "@/lib/stomp";

export function useMarkets() {

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

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadMarkets();

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
              (previous) => {

                const exists =
                  previous.some(
                    (market) =>
                      market.marketId ===
                      updatedMarket.marketId
                  );

                if (!exists) {

                  return [
                    updatedMarket,
                    ...previous,
                  ];

                }

                return previous.map(
                  (market) =>

                    market.marketId ===
                    updatedMarket.marketId

                      ? updatedMarket

                      : market
                );

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

      stompClient.onConnect =
        subscribe;

    }

    return () => {

      subscription?.unsubscribe();

    };

  }, []);

  return {

    markets,

    loading,

    refresh: loadMarkets,

  };

}