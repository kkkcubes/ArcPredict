"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  stompClient,
} from "@/lib/stomp";

const API_URL =
  process.env.NEXT_PUBLIC_API_URL!;

export function useRealtimeEvents() {

  const [events, setEvents] =
    useState<any[]>([]);

  const [loading, setLoading] =
    useState(true);

  const loadEvents =
    async () => {

      try {

        const response =
          await fetch(
            `${API_URL}/api/events`,
            {
              cache: "no-store",
            }
          );

        if (!response.ok) {

          throw new Error(
            "Failed to load activity feed"
          );

        }

        const data =
          await response.json();

        setEvents(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

  loadEvents();

  const subscribe =
    () => {

      stompClient.subscribe(
        "/topic/trades",
        (message) => {

          const event =
            JSON.parse(
              message.body
            );

          setEvents(
  (previous) =>

    [

      {

        id: Date.now(),

        eventType:
          event.eventType,

        marketId:
          event.marketId,

        wallet:
          event.wallet,

        amount:
          event.amount,

        position:
          "-",

        txHash:
          "",

        blockNumber:
          0,

        timestamp:
          event.timestamp,

        summary:
          event.eventType
            + " on Market #"
            + event.marketId,

      },

      ...previous,

    ].slice(0, 50)

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

}, []);

  return {

    events,

    loading,

    refresh: loadEvents,

  };

}