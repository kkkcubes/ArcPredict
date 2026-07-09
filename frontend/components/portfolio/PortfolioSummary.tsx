"use client";

import { Wallet, TrendingUp, CircleDollarSign, BarChart3 } from "lucide-react";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function PortfolioSummary() {
  
  const {
  portfolio,
  loading,
} = usePortfolioContext();

  if (loading) {
  return (
    <div
      className="
        grid
        grid-cols-1
        md:grid-cols-2
        xl:grid-cols-4
        gap-6
      "
    >
      {Array.from({ length: 4 }).map((_, index) => (
        <LoadingSkeleton
          key={index}
          className="
            h-44
            rounded-3xl
            bg-white
            border
            border-gray-200
          "
        />
      ))}
    </div>
  );
}

  const cards = [
    {
      title: "Total Invested",
      value: portfolio?.totalInvested ?? 0,
      color: "text-violet-600",
      icon: Wallet,
      suffix: "USDC",
    },
    {
      title: "YES Positions",
      value: portfolio?.yesPositions ?? 0,
      color: "text-green-600",
      icon: TrendingUp,
    },
    {
      title: "NO Positions",
      value: portfolio?.noPositions ?? 0,
      color: "text-red-600",
      icon: CircleDollarSign,
    },
    {
      title: "Total Trades",
      value: portfolio?.totalTrades ?? 0,
      color: "text-blue-600",
      icon: BarChart3,
    },
  ];

  return (
    <div
      className="
        grid
        grid-cols-1
        md:grid-cols-2
        xl:grid-cols-4
        gap-6
      "
    >
      {cards.map((card) => {
        const Icon = card.icon;

        return (
          <div
            key={card.title}
            className="
              dashboard-card
              p-6
              transition-all
              duration-300
              hover:-translate-y-1
              hover:shadow-xl
            "
          >
            <div className="flex items-center justify-between mb-6">

              <div>
                <p className="text-sm text-gray-500">
                  {card.title}
                </p>

                <h3
                  className={`
                    mt-3
                    text-4xl
                    font-black
                    ${card.color}
                  `}
                >
                  {card.value}

                  {card.suffix && (
                    <span className="ml-2 text-lg text-gray-500">
                      {card.suffix}
                    </span>
                  )}
                </h3>
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
                <Icon
                  size={26}
                  className="text-violet-600"
                />
              </div>

            </div>
          </div>
        );
      })}
    </div>
  );
}