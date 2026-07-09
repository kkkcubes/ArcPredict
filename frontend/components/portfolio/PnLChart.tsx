"use client";

import {
  TrendingUp,
} from "lucide-react";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function PnLChart() {

  const {
  portfolio,
  loading,
} = usePortfolioContext();

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
            Portfolio Overview
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-2">
            Total Invested
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-violet-100
            flex
            items-center
            justify-center
          "
        >
          <TrendingUp
            size={28}
            className="text-violet-600"
          />
        </div>

      </div>

      <div
        className="
          h-56
          rounded-3xl
          border
          border-dashed
          border-gray-300
          bg-gradient-to-br
          from-violet-50
          to-white
          flex
          flex-col
          items-center
          justify-center
        "
      >

        <span
          className="
            text-6xl
            font-black
            text-violet-600
          "
        >
          {portfolio?.totalInvested ?? 0}
        </span>

        <span className="mt-3 text-gray-500">
          USDC Invested
        </span>

      </div>

    </div>

  );
}