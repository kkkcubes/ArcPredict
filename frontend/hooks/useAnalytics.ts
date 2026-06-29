"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  analyticsService,
} from "@/services/analyticsService";

import {
  stompClient,
} from "@/lib/stomp";

export function useAnalytics() {

  const [analytics, setAnalytics] =
    useState<any>(null);

  const [loading, setLoading] =
    useState(true);

  const loadAnalytics =
    async () => {

      try {

        const data =
  await analyticsService
    .getAnalytics();

console.log(
  "ANALYTICS RESPONSE:",
  data
);

setAnalytics(
  data
);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }
    };

  useEffect(() => {

  loadAnalytics();

  let subscription: any;

  const subscribe = () => {

    subscription = stompClient.subscribe(
      "/topic/analytics",
      (message) => {
        setAnalytics(JSON.parse(message.body));
      }
    );

  };

  if (stompClient.connected) {
    subscribe();
  } else {
    const previous = stompClient.onConnect;

    stompClient.onConnect = (frame) => {
      previous?.(frame);
      subscribe();
    };
  }

  return () => {
    subscription?.unsubscribe();
  };

}, []);

  return {

    analytics,

    loading,

    refresh:
      loadAnalytics,
  };
}