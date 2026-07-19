"use client";

import {
  Award,
  Sparkles,
} from "lucide-react";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";

import LoadingSkeleton
  from "@/components/ui/LoadingSkeleton";

export default function RewardsPanel() {

  const {
    portfolio,
    loading,
  } = usePortfolioContext();

  const totalTrades =
    portfolio?.totalTrades ?? 0;

  const activityLevel =
    totalTrades === 0
      ? "Inactive"
      : totalTrades < 10
        ? "Beginner"
        : totalTrades < 50
          ? "Active"
          : "Power Trader";

  const activityProgress =
    Math.min(
      (totalTrades / 50) * 100,
      100
    );

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

  return (

    <div className="dashboard-card p-8">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Portfolio Rewards
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-2">
            Trading Activity
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-amber-100
            flex
            items-center
            justify-center
          "
        >

          <Award
            size={28}
            className="text-amber-600"
          />

        </div>

      </div>

      <div
        className="
          rounded-3xl
          border
          border-gray-200
          bg-gradient-to-br
          from-amber-50
          to-white
          p-8
        "
      >

        <div className="flex items-center justify-between">

          <div>

            <p className="text-sm text-gray-500">
              Total Trades
            </p>

            <h3
              className="
                mt-3
                text-6xl
                font-black
                text-amber-600
              "
            >
              {totalTrades}
            </h3>

          </div>

          <Sparkles
            size={48}
            className="text-amber-500"
          />

        </div>

        <div className="mt-8">

          <div className="flex justify-between text-sm text-gray-500 mb-2">

            <span>
              Activity Level
            </span>

            <span>
              {activityLevel}
            </span>

          </div>

          <div
            className="
              h-3
              rounded-full
              bg-gray-200
              overflow-hidden
            "
          >

            <div
              className="
                h-full
                rounded-full
                bg-amber-500
                transition-all
                duration-500
              "
              style={{
                width: `${activityProgress}%`,
              }}
            />

          </div>

        </div>

      </div>

    </div>

  );

}