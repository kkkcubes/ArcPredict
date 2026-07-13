"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";

import {
  useAccount,
} from "wagmi";

import {
  stompClient,
} from "@/lib/stomp";

interface PortfolioContextType {

  portfolio: any;

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
    loading,
    setLoading,
  ] = useState(true);

  const loadPortfolio =
    async () => {

      if (!address) {

        setPortfolio(null);

        setLoading(false);

        return;

      }

      try {

        const response =
  await fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/api/portfolio/${address}`
  );

        const data =
          await response.json();

        setPortfolio(data);

      } catch (error) {

        console.error(
          "Portfolio load failed",
          error
        );

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadPortfolio();

  }, [address]);

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

              setPortfolio(updated);

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

  }, [address]);

  return (

    <PortfolioContext.Provider
      value={{

        portfolio,

        loading,

        refresh:
          loadPortfolio,

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