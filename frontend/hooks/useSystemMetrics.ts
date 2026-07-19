"use client";

import { useEffect, useState } from "react";

import {
  systemService,
} from "@/services/systemService";

export function useSystemMetrics() {

    const [metrics, setMetrics] =
        useState<any>(null);

    const [loading, setLoading] =
        useState(true);

    const loadMetrics =
        async () => {

            try {

                const data =
  await systemService
    .getMetrics();

                setMetrics(data);

            } catch (error) {

                console.error(error);

            } finally {

                setLoading(false);

            }

        };

    useEffect(() => {

    loadMetrics();

    const interval = setInterval(() => {

        loadMetrics();

    }, 30000);

    return () => {

        clearInterval(interval);

    };

}, []);

    return {

        metrics,

        loading,

        refresh: loadMetrics,

    };

}