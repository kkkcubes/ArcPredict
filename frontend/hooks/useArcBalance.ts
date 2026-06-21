"use client";

import {
  useEffect,
  useState
} from "react";

import {
  arcService
} from "@/services/arcService";

export function useArcBalance() {

  const [vaultBalance, setVaultBalance] =
    useState("0");

  const [liquidity, setLiquidity] =
    useState("0");

  const [loading, setLoading] =
    useState(true);

  const loadBalance =
    async () => {

      try {

        const balance: any =
          await arcService
            .getVaultBalance();

        const available: any =
          await arcService
            .getAvailableLiquidity();

        setVaultBalance(
          balance.toString()
        );

        setLiquidity(
          available.toString()
        );

      } finally {

        setLoading(false);
      }
    };

  useEffect(() => {
    loadBalance();
  }, []);

  return {
    vaultBalance,
    liquidity,
    loading,
    refresh:
      loadBalance,
  };
}