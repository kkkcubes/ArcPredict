"use client";

import { useLeaderboard } from "@/hooks/useLeaderboard";

export default function LeaderboardWidget() {

  const {
    leaderboard,
    loading,
  } = useLeaderboard();

  if (loading) {

    return (
      <div className="card p-6">
        Loading leaderboard...
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
        Top Traders
      </h2>

      <div className="space-y-4">

        {leaderboard
          .slice(0, 3)
          .map(
            (
              trader: any,
              index: number
            ) => (

              <div
                key={trader.wallet}
                className="
                  flex
                  justify-between
                  items-center
                  border-b
                  border-gray-800
                  pb-3
                "
              >

                <div>

                  <p className="font-bold">

                    {index === 0 && "🥇"}
                    {index === 1 && "🥈"}
                    {index === 2 && "🥉"}

                    {" "}
                    Rank #{index + 1}

                  </p>

                  <p className="text-gray-400">

                    {trader.wallet.slice(0, 6)}
                    ...
                    {trader.wallet.slice(-4)}

                  </p>

                </div>

                <div className="text-right">

                  <p>
                    {trader.totalVolume}
                    {" "}
                    USDC
                  </p>

                  <p className="text-gray-400">

                    {trader.totalTrades}
                    {" "}
                    trades

                  </p>

                </div>

              </div>

            )
          )}

      </div>

    </div>

  );
}