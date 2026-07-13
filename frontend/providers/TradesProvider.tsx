"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import {
  stompClient,
} from "@/lib/stomp";

interface TradesContextType {

  trades: any[];

  loading: boolean;

  error: Error | null;

  refresh: () => Promise<void>;

}

const TradesContext =
  createContext<TradesContextType | null>(
    null
  );

export function TradesProvider({
  children,
}: {
  children: React.ReactNode;
}) {

  const [
    trades,
    setTrades,
  ] = useState<any[]>([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const [
    error,
    setError,
  ] =
    useState<Error | null>(null);

  const loadTrades =
    async () => {

      try {

        setLoading(true);

        const response =
          await fetch(
            `${process.env.NEXT_PUBLIC_API_URL}/api/trades`
          );

        if (!response.ok) {

          throw new Error(
            "Failed to load trades"
          );

        }

        const data =
          await response.json();

        setTrades(data);

      } catch (err) {

        setError(
          err as Error
        );

        console.error(err);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadTrades();

    let subscription: any;

    const subscribe =
      () => {

        subscription =
          stompClient.subscribe(

            "/topic/trades",

            (message) => {

              const trade =
                JSON.parse(
                  message.body
                );

              setTrades(
                previous => [

                  trade,

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

      const previous =
        stompClient.onConnect;

      stompClient.onConnect =
        frame => {

          previous?.(
            frame
          );

          subscribe();

        };

    }

    return () => {

      subscription?.unsubscribe();

    };

  }, []);

  return (

    <TradesContext.Provider
      value={{
        trades,
        loading,
        error,
        refresh: loadTrades,
      }}
    >

      {children}

    </TradesContext.Provider>

  );

}

export function useTradesContext() {

  const context =
    useContext(
      TradesContext
    );

  if (!context) {

    throw new Error(
      "useTradesContext must be used inside TradesProvider"
    );

  }

  return context;

}