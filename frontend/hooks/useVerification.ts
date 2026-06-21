"use client";

import {
  useEffect,
  useState
} from "react";

import {
  verificationService
} from "@/services/verificationService";

export function useVerification() {

  const [network, setNetwork] =
    useState<any>(null);

  const [contracts, setContracts] =
    useState<any>(null);

  const [loading, setLoading] =
    useState(true);

  const loadVerification =
    async () => {

      try {

        const networkData =
          await verificationService
            .getNetworkStatus();

        const contractData =
          await verificationService
            .getContracts();

        setNetwork(
          networkData
        );

        setContracts(
          contractData
        );

      } finally {

        setLoading(false);
      }
    };

  useEffect(() => {
    loadVerification();
  }, []);

  return {
    network,
    contracts,
    loading,
    refresh:
      loadVerification,
  };
}