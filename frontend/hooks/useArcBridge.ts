"use client";

import {
  useState
} from "react";

export function useArcBridge() {

  const [loading, setLoading] =
    useState(false);

  const bridgeUSDC =
    async (
      amount: string
    ) => {

      try {

        setLoading(true);

        return true;

      } catch (error) {

        console.error(
          error
        );

        return false;

      } finally {

        setLoading(false);

      }

    };

  return {

    bridgeUSDC,
    loading,

  };

}