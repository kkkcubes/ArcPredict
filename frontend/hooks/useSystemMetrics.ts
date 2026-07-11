"use client";

import { useEffect, useState } from "react";

const API_URL =
    process.env.NEXT_PUBLIC_API_URL!;

export function useSystemMetrics() {

    const [metrics, setMetrics] =
        useState<any>(null);

    const [loading, setLoading] =
        useState(true);

    const loadMetrics =
        async () => {

            try {

                const response =
                    await fetch(
                        `${API_URL}/api/system/metrics`,
                        {
                            cache: "no-store",
                        }
                    );

                if (!response.ok) {

                    throw new Error(
                        "Failed to load system metrics"
                    );

                }

                const data =
                    await response.json();

                setMetrics(data);

            } catch (error) {

                console.error(error);

            } finally {

                setLoading(false);

            }

        };

    useEffect(() => {

        loadMetrics();

    }, []);

    return {

        metrics,

        loading,

        refresh: loadMetrics,

    };

}