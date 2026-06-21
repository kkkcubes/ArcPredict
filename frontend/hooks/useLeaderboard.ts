"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  getLeaderboard,
} from "@/services/backendLeaderboardService";

import {
  stompClient,
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

        setLoading(false);

      }

    };

  useEffect(() => {

    loadLeaderboard();

    const subscribe = () => {

  stompClient.subscribe(
    "/topic/leaderboard",
    (message) => {

      const data =
        JSON.parse(
          message.body
        );

      setLeaderboard(
        data
      );

    }
  );

};

if (
  stompClient.connected
) {

  subscribe();

} else {

  stompClient.onConnect =
    subscribe;

}

    return () => {};

  }, []);

  return {

    leaderboard,

    loading,

    refresh:
      loadLeaderboard,
  };
}