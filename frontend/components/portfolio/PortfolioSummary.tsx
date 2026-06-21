"use client";

import { usePortfolio } from "@/hooks/usePortfolio";

export default function PortfolioSummary() {

  const {
    portfolio,
    loading,
  } = usePortfolio();

  if (loading) {
    return (
      <div>
        Loading Portfolio...
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

      <div className="card p-6">

        <h3>
          Total Invested
        </h3>

        <p
          className="
            text-4xl
            font-bold
          "
        >
          {portfolio?.totalInvested ?? 0}
        </p>

      </div>

      <div className="card p-6">

        <h3>
          YES Positions
        </h3>

        <p
          className="
            text-4xl
            font-bold
            text-green-500
          "
        >
          {portfolio?.yesPositions ?? 0}
        </p>

      </div>

      <div className="card p-6">

        <h3>
          NO Positions
        </h3>

        <p
          className="
            text-4xl
            font-bold
            text-red-500
          "
        >
          {portfolio?.noPositions ?? 0}
        </p>

      </div>

      <div className="card p-6">

        <h3>
          Total Trades
        </h3>

        <p
          className="
            text-4xl
            font-bold
          "
        >
          {portfolio?.totalTrades ?? 0}
        </p>

      </div>

    </div>
  );
}