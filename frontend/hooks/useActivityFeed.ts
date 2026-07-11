"use client";

import {
  useEffect,
  useState,
} from "react";

const API_URL =
  process.env
    .NEXT_PUBLIC_API_URL!;

export function useActivityFeed() {

  const [
    activities,
    setActivities,
  ] = useState<any[]>([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const loadActivities =
    async () => {

      try {

        const response =
          await fetch(
            `${API_URL}/api/events`,
            {
              cache: "no-store",
            }
          );

        if (!response.ok) {

          throw new Error(
            "Failed to load activity feed"
          );

        }

        const data =
          await response.json();

        setActivities(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    };

  useEffect(() => {

    loadActivities();

  }, []);

  return {

    activities,

    loading,

    refresh: loadActivities,

  };

}