"use client";

import {
  useEffect,
} from "react";

import {
  subscribe,
} from "@/lib/stomp";

export function useBackendRealtime(
  onMarket: (payload: any) => void,
  onTrade: (payload: any) => void,
  onEvent?: (payload: any) => void
) {

  useEffect(() => {

  const unsubscribers: (() => void)[] = [];

  const marketSubscription =
    subscribe(
      "/topic/markets",
      (message) => {

        onMarket(
          JSON.parse(
            message.body
          )
        );

      }
    );

  if (marketSubscription) {

    unsubscribers.push(
      marketSubscription
    );

  }

  const tradeSubscription =
    subscribe(
      "/topic/trades",
      (message) => {

        onTrade(
          JSON.parse(
            message.body
          )
        );

      }
    );

  if (tradeSubscription) {

    unsubscribers.push(
      tradeSubscription
    );

  }

  if (onEvent) {

    const eventSubscription =
      subscribe(
        "/topic/events",
        (message) => {

          onEvent(
            JSON.parse(
              message.body
            )
          );

        }
      );

    if (eventSubscription) {

      unsubscribers.push(
        eventSubscription
      );

    }

  }

  return () => {

    unsubscribers.forEach(

      (unsubscribe) => {

        unsubscribe();

      }

    );

  };

}, [onMarket, onTrade, onEvent]);

}