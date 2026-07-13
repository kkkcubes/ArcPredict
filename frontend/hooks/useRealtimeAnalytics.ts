"use client";

import {
  useEffect
} from "react";

import {
  subscribe,
} from "@/lib/stomp";

export function useRealtimeAnalytics(
  callback: (
    data: any
  ) => void
) {

  useEffect(() => {

    const unsubscribe =
  subscribe(

    "/topic/analytics",

    (message) => {

      callback(
        JSON.parse(
          message.body
        )
      );

    }

  );

return unsubscribe;

  }, [callback]);

}