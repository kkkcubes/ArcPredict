"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  activityService,
} from "@/services/activityService";

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

        const data =
  await activityService
    .getActivityFeed();

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