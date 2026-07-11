"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  portfolioService,
} from "@/services/portfolioService";

export function
usePortfolioAnalytics(
  wallet?: string
) {

  const [
    analytics,
    setAnalytics,
  ] = useState<any>(null);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const [
    error,
    setError,
  ] = useState<
    Error | null
  >(null);

  useEffect(() => {

    if (
      !wallet
    ) {

      setLoading(false);

      return;

    }

    const load =
      async () => {

        try {

          setLoading(true);

          const data =
            await portfolioService
              .getPortfolioAnalytics(
                wallet
              );

          setAnalytics(
            data
          );

        } catch (err) {

          setError(
            err as Error
          );

        } finally {

          setLoading(false);

        }

      };

    load();

  }, [wallet]);

  return {

    analytics,

    loading,

    error,

  };

}