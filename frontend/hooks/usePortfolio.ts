"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  useAccount,
} from "wagmi";

import {
  stompClient,
} from "@/lib/stomp";

export function usePortfolio() {

  const {
    address,
  } = useAccount();

  const [portfolio, setPortfolio] =
    useState<any>(null);

  const [loading, setLoading] =
    useState(true);

  const loadPortfolio =
    async () => {

      if (!address) {

        setLoading(false);
        return;
      }

      try {

        const response =
          await fetch(
            `${process.env.NEXT_PUBLIC_API_URL}/portfolio/${address}`
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

    stompClient.onConnect =
      () => {

        stompClient.subscribe(
          "/topic/portfolio",
          (message) => {

            const updatedPortfolio =
              JSON.parse(
                message.body
              );

            if (
              updatedPortfolio.wallet
                ?.toLowerCase() ===
              address.toLowerCase()
            ) {

              setPortfolio(
                updatedPortfolio
              );

            }

          }
        );

      };

    stompClient.activate();

    return () => {

      stompClient.deactivate();

    };

  }, [address]);

  return {
    portfolio,
    loading,
    refresh:
      loadPortfolio,
  };
}