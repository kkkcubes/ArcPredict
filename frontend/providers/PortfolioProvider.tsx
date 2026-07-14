"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
  useCallback,
} from "react";

import {
  useAccount,
} from "wagmi";

import {
  stompClient,
} from "@/lib/stomp";

import {
  portfolioService,
} from "@/services/portfolioService";

interface PortfolioContextType {

    portfolio: any;

    analytics: any;

    loading: boolean;

    refresh: () => Promise<void>;

}

const PortfolioContext =
  createContext<PortfolioContextType | null>(
    null
  );

export function PortfolioProvider({
  children,
}: {
  children: React.ReactNode;
}) {

  const {
    address,
  } = useAccount();

  const [
    portfolio,
    setPortfolio,
  ] = useState<any>(null);

  const [
    analytics,
    setAnalytics,
  ] = useState<any>(null);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const refresh = useCallback(
  async () => {

      if (!address) {

        setPortfolio(null);

        setAnalytics(null);

        setLoading(false);

        return;

      }

      try {

        setLoading(true);

        const response =
          await fetch(
            `${process.env.NEXT_PUBLIC_API_URL}/api/portfolio/${address}`
          );

        const data =
          await response.json();

        setPortfolio(
          data
        );

        const analyticsData =
          await portfolioService
            .getPortfolioAnalytics(
              address
            );

        setAnalytics(
          analyticsData
        );

      } catch (error) {

        console.error(
          "Portfolio load failed",
          error
        );

      } finally {

        setLoading(false);

      }

      },
  [address]
);

  useEffect(() => {

  refresh();

}, [refresh]);

  useEffect(() => {

    if (!address) {
      return;
    }

    let subscription: any;

    const subscribe = () => {

      subscription =
        stompClient.subscribe(

          "/topic/portfolio",

          (message) => {

            const updated =
              JSON.parse(
                message.body
              );

            if (
              updated.wallet?.toLowerCase()
              ===
              address.toLowerCase()
            ) {

refresh();

            }

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

  },  [address, refresh]);

  return (

    <PortfolioContext.Provider
      value={{

    portfolio,

    analytics,

    loading,

    refresh,

}}
    >

      {children}

    </PortfolioContext.Provider>

  );

}

export function usePortfolioContext() {

  const context =
    useContext(
      PortfolioContext
    );

  if (!context) {

    throw new Error(
      "usePortfolioContext must be used inside PortfolioProvider"
    );

  }

  return context;

}