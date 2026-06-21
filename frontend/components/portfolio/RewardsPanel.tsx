"use client";

import { usePortfolio } from "@/hooks/usePortfolio";

export default function RewardsPanel() {

  const {
    portfolio,
    loading,
  } = usePortfolio();

  if (loading) {
    return (
      <div className="card p-6">
        Loading Rewards...
      </div>
    );
  }

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-4
        "
      >
        Portfolio Stats
      </h2>

      <div>

        <p
          className="
            text-gray-400
          "
        >
          Total Trades
        </p>

        <p
          className="
            text-5xl
            font-bold
            text-blue-500
          "
        >
          {portfolio?.totalTrades ?? 0}
        </p>

      </div>

    </div>
  );
}