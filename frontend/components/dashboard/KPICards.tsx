"use client";

import { useAnalytics } from "@/hooks/useAnalytics";

export default function KPICards() {

  const {
    analytics,
    loading,
  } = useAnalytics();

  if (loading) {
    return (
      <div>
        Loading KPIs...
      </div>
    );
  }

  return (
    <div
      className="
        grid
        grid-cols-1
        md:grid-cols-4
        gap-4
      "
    >
      <div className="card p-5">
        <h3>Total Markets</h3>
        <p className="text-3xl font-bold">
          {analytics?.totalMarkets}
        </p>
      </div>

      <div className="card p-5">
        <h3>Total Volume</h3>
        <p className="text-3xl font-bold">
          {analytics?.totalVolume}
        </p>
      </div>

      <div className="card p-5">
        <h3>YES Pool</h3>
        <p className="text-3xl font-bold text-green-500">
          {analytics?.yesPool}
        </p>
      </div>

      <div className="card p-5">
        <h3>NO Pool</h3>
        <p className="text-3xl font-bold text-red-500">
          {analytics?.noPool}
        </p>
      </div>
    </div>
  );
}