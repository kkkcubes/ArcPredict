"use client";

import {
  Trophy,
  Medal,
  Award,
} from "lucide-react";

import { useLeaderboard } from "@/hooks/useLeaderboard";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";
import EmptyState from "@/components/ui/EmptyState";

export default function LeaderboardWidget() {

  const {
    leaderboard,
    loading,
  } = useLeaderboard();

  if (loading) {
  return (
    <LoadingSkeleton
      className="
        h-[420px]
        rounded-3xl
        bg-white
        border
        border-gray-200
      "
    />
  );
}

if (leaderboard.length === 0) {
    return (
        <section
            id="leaderboard"
            className="dashboard-card p-8"
        >
            <EmptyState
                title="No Leaderboard Data"
                description="Leaderboard rankings will appear once users start trading."
            />
        </section>
    );
}

  return (

    <section
      id="leaderboard"
      className="dashboard-card p-8"
    >

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Community
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Leaderboard
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-yellow-100
            flex
            items-center
            justify-center
          "
        >
          <Trophy
            size={26}
            className="text-yellow-600"
          />
        </div>

      </div>

      <div className="overflow-x-auto">

        <table
  className="
    w-full
    min-w-[640px]
  "
>

          <thead>

            <tr className="border-b border-gray-200">

              <th className="text-left py-4 text-sm text-gray-500">
                Rank
              </th>

              <th className="text-left py-4 text-sm text-gray-500">
                Wallet
              </th>

              <th className="text-right py-4 text-sm text-gray-500">
                Volume
              </th>

              <th className="text-right py-4 text-sm text-gray-500">
                Trades
              </th>

            </tr>

          </thead>

          <tbody>

            {leaderboard
              .slice(0, 5)
              .map(
                (
                  trader: any,
                  index: number
                ) => {

                  const Icon =
                    index === 0
                      ? Trophy
                      : index === 1
                      ? Medal
                      : Award;

                  const color =
                    index === 0
                      ? "text-yellow-500"
                      : index === 1
                      ? "text-gray-500"
                      : "text-amber-600";

                  return (

                    <tr
                      key={trader.wallet}
                      className="
  border-b
  border-gray-100
  transition-all
  duration-300
  hover:bg-violet-50
"
                    >

                      <td className="py-5">

                        <div className="flex items-center gap-3">

                          <Icon
                            size={20}
                            className={color}
                          />

                          <span className="font-bold">
                            #{index + 1}
                          </span>

                        </div>

                      </td>

                      <td className="py-5">

                        <span
  className="
    font-semibold
    text-gray-900
  "
>

                          {trader.wallet.slice(0, 6)}
                          ...
                          {trader.wallet.slice(-4)}

                        </span>

                      </td>

                      <td
  className="
    py-5
    text-right
    font-bold
    text-violet-600
  "
>

                        {trader.totalVolume} USDC

                      </td>

                      <td
  className="
    py-5
    text-right
    font-medium
    text-gray-700
  "
>

                        {trader.totalTrades}

                      </td>

                    </tr>

                  );

                }
              )}

          </tbody>

        </table>

      </div>

    </section>

  );
}