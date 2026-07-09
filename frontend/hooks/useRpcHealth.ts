"use client";

import {
  useEffect,
  useState,
} from "react";

const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export interface RpcHealth {

  connected: boolean;

  latency: number;

  chainId: number;

  latestBlock: number;

}

export function useRpcHealth() {

  const [rpc, setRpc] =
    useState<RpcHealth | null>(null);

  const [loading, setLoading] =
    useState(true);

  const loadRpc =
    async () => {

      try {

        const response =
          await fetch(

            `${API_URL}/api/system/rpc`,

            {
              cache: "no-store",
            }

          );

        const data =
          await response.json();

        setRpc(data);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadRpc();

  }, []);

  return {

    rpc,

    loading,

    refresh: loadRpc,

  };

}