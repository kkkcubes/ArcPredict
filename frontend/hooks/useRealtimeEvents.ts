"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  stompClient,
} from "@/lib/stomp";

export function useRealtimeEvents() {

  const [events, setEvents] =
    useState<any[]>([]);

  useEffect(() => {

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
              (previous) => [
                event,
                ...previous,
              ]
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
  };
}