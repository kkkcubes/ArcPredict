"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import {
  analyticsService,
} from "@/services/analyticsService";

import {
  stompClient,
} from "@/lib/stomp";

interface AnalyticsContextType {

  analytics: any;

  loading: boolean;

  refresh: () => Promise<void>;

}

const AnalyticsContext =
  createContext<AnalyticsContextType | null>(
    null
  );

export function AnalyticsProvider({
  children,
}: {
  children: React.ReactNode;
}) {

  const [
    analytics,
    setAnalytics,
  ] = useState<any>(null);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const loadAnalytics =
    async () => {

      try {

        const data =
          await analyticsService.getAnalytics();

        setAnalytics(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadAnalytics();

  }, []);

  useEffect(() => {

    let subscription: any;

    const subscribe = () => {

      subscription =
        stompClient.subscribe(

          "/topic/analytics",

          (message) => {

            setAnalytics(
              JSON.parse(
                message.body
              )
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
        (frame) => {

          previous?.(frame);

          subscribe();

        };

    }

    return () => {

      subscription?.unsubscribe();

    };

  }, []);

  return (

    <AnalyticsContext.Provider
      value={{

        analytics,

        loading,

        refresh:
          loadAnalytics,

      }}
    >

      {children}

    </AnalyticsContext.Provider>

  );

}

export function useAnalyticsContext() {

  const context =
    useContext(
      AnalyticsContext
    );

  if (!context) {

    throw new Error(
      "useAnalyticsContext must be used inside AnalyticsProvider"
    );

  }

  return context;

}