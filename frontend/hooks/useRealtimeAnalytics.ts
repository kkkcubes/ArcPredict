"use client";

import {
  useEffect
} from "react";

import {
  stompClient
} from "@/lib/stomp";

export function useRealtimeAnalytics(
  callback: (
    data: any
  ) => void
) {

  useEffect(() => {

    if (!stompClient.active) {

      stompClient.activate();

    }

    stompClient.onConnect =
      () => {

        const sub =
          stompClient.subscribe(

            "/topic/analytics",

            (message) => {

              callback(
                JSON.parse(
                  message.body
                )
              );

            }
          );

        return () => {

          sub.unsubscribe();

        };
      };

  }, [callback]);

}