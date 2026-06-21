"use client";

import {
  useLeaderboard,
} from "@/hooks/useLeaderboard";

export default function LeaderboardPage() {

  const {
    leaderboard,
    loading,
  } = useLeaderboard();

  if (loading) {

    return (
      <main className="container py-10">
        Loading Leaderboard...
      </main>
    );

  }

  return (

    <main className="container py-10">

      <h1
        className="
          text-5xl
          font-bold
          mb-8
        "
      >
        Leaderboard
      </h1>

      <div className="space-y-4">

        {leaderboard.map(
          (
            trader: any,
            index: number
          ) => (

            <div
              key={trader.wallet}
              className="
                card
                p-6
              "
            >

              <div className="flex justify-between">

                <div>

                  <h2
                    className="
                      text-xl
                      font-bold
                    "
                  >
                    #{index + 1}
                  </h2>

                  <p>
                    {trader.wallet}
                  </p>

                </div>

                <div className="text-right">

                  <p>
                    Volume:
                    {" "}
                    {trader.totalVolume}
                  </p>

                  <p>
                    Trades:
                    {" "}
                    {trader.totalTrades}
                  </p>

                </div>

              </div>

            </div>

          )
        )}

      </div>

    </main>

  );
}