"use client";

import { usePortfolio }
  from "@/hooks/usePortfolio";

export default function PortfolioPage() {

  const {
    portfolio,
    loading,
  } = usePortfolio();

  if (loading) {

    return (
      <main className="container">
        Loading Portfolio...
      </main>
    );
  }

  if (!portfolio) {

    return (
      <main className="container">
        Connect Wallet
      </main>
    );
  }

  return (

    <main className="container">

      <h1
        className="
          text-5xl
          font-bold
          mb-8
        "
      >
        Portfolio
      </h1>

      <div
        className="
          grid
          md:grid-cols-4
          gap-6
        "
      >

        <div className="card p-6">

          <p className="text-gray-400">
            Total Invested
          </p>

          <h2
            className="
              text-4xl
              font-bold
              mt-2
            "
          >
            {portfolio.totalInvested}
          </h2>

        </div>

        <div className="card p-6">

          <p className="text-gray-400">
            YES Positions
          </p>

          <h2
            className="
              text-4xl
              font-bold
              text-green-500
              mt-2
            "
          >
            {portfolio.yesPositions}
          </h2>

        </div>

        <div className="card p-6">

          <p className="text-gray-400">
            NO Positions
          </p>

          <h2
            className="
              text-4xl
              font-bold
              text-red-500
              mt-2
            "
          >
            {portfolio.noPositions}
          </h2>

        </div>

        <div className="card p-6">

          <p className="text-gray-400">
            Total Trades
          </p>

          <h2
            className="
              text-4xl
              font-bold
              mt-2
            "
          >
            {portfolio.totalTrades}
          </h2>

        </div>

      </div>

    </main>

  );
}