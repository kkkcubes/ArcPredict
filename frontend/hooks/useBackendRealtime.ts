"use client";

import {
  useEffect,
} from "react";

import {
  stompClient,
} from "@/lib/stomp";

export function useBackendRealtime(
  onMarket: (payload: any) => void,
  onTrade: (payload: any) => void,
  onEvent?: (payload: any) => void
) {

  useEffect(() => {

    const subscribe = () => {

      stompClient.subscribe(
        "/topic/markets",
        (message) => {

          onMarket(
            JSON.parse(
              message.body
            )
          );

        }
      );

      stompClient.subscribe(
        "/topic/trades",
        (message) => {

          onTrade(
            JSON.parse(
              message.body
            )
          );

        }
      );

      if (onEvent) {

        stompClient.subscribe(
          "/topic/events",
          (message) => {

            onEvent(
              JSON.parse(
                message.body
              )
            );

          }
        );

      }

    };

    if (
      stompClient.connected
    ) {

      subscribe();

    } else {

      stompClient.onConnect =
        subscribe;

    }

  }, [onMarket, onTrade, onEvent]);

}