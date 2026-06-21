"use client";

import { useAnalytics }
  from "@/hooks/useAnalytics";

export default function VolumeChart() {

  const {
    analytics,
  } = useAnalytics();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Volume Overview
      </h2>

      <div className="text-5xl font-bold">

        {analytics?.totalVolume || 0}

      </div>

      <p className="text-gray-400 mt-2">
        Total On-Chain Volume
      </p>

    </div>
  );
}