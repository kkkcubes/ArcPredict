"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  analyticsService,
} from "@/services/analyticsService";

export function useAnalyticsHistory() {

  const [history, setHistory] =
    useState<any>(null);

  const [loading, setLoading] =
    useState(true);

  const loadHistory =
    async () => {

      try {

        const data =
          await analyticsService
            .getAnalyticsHistory();

        setHistory(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadHistory();

  }, []);

  return {

    history,

    loading,

    refresh: loadHistory,

  };

}