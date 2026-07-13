"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  subscribe,
} from "@/lib/stomp";

import {
  eventService,
} from "@/services/eventService";

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

        const data =
  await eventService
    .getEvents();

        setEvents(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

  loadEvents();

  const unsubscribe =
  subscribe(

    "/topic/trades",

    (message) => {

      const event =
        JSON.parse(
          message.body
        );

      setEvents(
        (previous) => [

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
              event.eventType +
              " on Market #" +
              event.marketId,

          },

          ...previous,

        ].slice(0, 50)
      );

    }

  );

return unsubscribe;

}, []);

  return {

    events,

    loading,

    refresh: loadEvents,

  };

}