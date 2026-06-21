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

  const loadStats =
    async () => {

      try {

        const data =
          await getDashboardStats();

        setStats(data);

      } finally {

        setLoading(false);
      }
    };

  useEffect(() => {
    loadStats();
  }, []);

  return {
    stats,
    loading,
    refresh: loadStats,
    setStats,
  };
}