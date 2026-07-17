"use client";

import {
  useEffect,
  useState
} from "react";

import {
  getDashboardStats
} from "@/services/backendAnalyticsService";

export function useDashboardStats() {

  const [stats, setStats] =
    useState<any>(null as any);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState<string | null>(
      null
    );

  const loadStats =
    async () => {

      try {

        setError(
          null
        );

        const data =
          await getDashboardStats();

        setStats(
          data
        );

      } catch (err) {

        setError(
          err instanceof Error
            ? err.message
            : "Failed to load dashboard"
        );

      } finally {

        setLoading(
          false
        );

      }

    };

  useEffect(() => {

    loadStats();

  }, []);

  return {

    stats,
    loading,
    error,
    refresh: loadStats,
    setStats,

  };

}