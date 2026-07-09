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

        setLoading(
          false
        );

      }

    };

  useEffect(() => {

    loadLeaderboard();

    let subscription: any;

    const subscribe = () => {

      subscription =
        stompClient.subscribe(

          "/topic/leaderboard",

          (message) => {

            setLeaderboard(
              JSON.parse(
                message.body
              )
            );

          }

        );

    };

    if (
      stompClient.connected
    ) {

      subscribe();

    } else {

      const previous =
        stompClient.onConnect;

      stompClient.onConnect =
        (frame) => {

          previous?.(
            frame
          );

          subscribe();

        };

    }

    return () => {

      subscription?.unsubscribe();

    };

  }, []);

  return {

    leaderboard,

    loading,

    refresh:
      loadLeaderboard,

  };

}