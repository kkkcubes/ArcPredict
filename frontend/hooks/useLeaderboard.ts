"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  getLeaderboard,
} from "@/services/backendLeaderboardService";

import {
  subscribe,
} from "@/lib/stomp";

export function useLeaderboard() {

  const [
    leaderboard,
    setLeaderboard,
  ] = useState<any[]>([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const loadLeaderboard =
    async () => {

      try {

        const data =
          await getLeaderboard();

        setLeaderboard(
          data
        );

      } catch (error) {

        console.error(
          error
        );

      } finally {

        setLoading(
          false
        );

      }

    };

  useEffect(() => {

    loadLeaderboard();

    const unsubscribe =
      subscribe(

        "/topic/leaderboard",

        (message) => {

          setLeaderboard(
            JSON.parse(
              message.body
            )
          );

        }

      );

    return unsubscribe;

  }, []);

  return {

    leaderboard,

    loading,

    refresh:
      loadLeaderboard,

  };

}