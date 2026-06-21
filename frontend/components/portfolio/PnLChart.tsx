"use client";

import { usePortfolio } from "@/hooks/usePortfolio";

export default function PnLChart() {

  const {
    portfolio,
    loading,
  } = usePortfolio();

  if (loading) {
    return (
      <div className="card p-6">
        Loading PnL...
      </div>
    );
  }

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Portfolio Overview
      </h2>

      <div
        className="
          h-40
          flex
          items-center
          justify-center
        "
      >

        <span
          className="
            text-5xl
            font-bold
            text-blue-500
          "
        >
          {portfolio?.totalInvested ?? 0}
        </span>

      </div>

      <p
        className="
          text-center
          text-gray-400
        "
      >
        Total Invested
      </p>

    </div>
  );
}