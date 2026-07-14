"use client";

import {
  Wallet,
  TrendingUp,
  CircleDollarSign,
  BarChart3,
} from "lucide-react";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";


import {
  formatCurrency,
  formatNumber,
  formatPercentage,
} from "@/lib/format";

import LoadingSkeleton
  from "@/components/ui/LoadingSkeleton";

export default function PortfolioSummary() {

 const {

    portfolio,

    analytics,

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

        {Array.from({
          length: 8,
        }).map((_, index) => (

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

 console.log("CARDS =", [
  {
    title: "Total Invested",
    value:
      analytics?.totalInvested ??
      portfolio?.totalInvested ??
      0,
  },
  {
    title: "Current Value",
    value:
      analytics?.currentValue ??
      0,
  },
  {
    title: "ROI",
    value:
      analytics?.roi ??
      0,
  },
]);

const cards = [

    {
      title: "Total Invested",
      value:
        analytics?.totalInvested ??
        portfolio?.totalInvested ??
        0,
      color: "text-violet-600",
      icon: Wallet,
      suffix: "USDC",
    },

    {
      title: "Current Value",
      value:
        analytics?.currentValue ??
        0,
      color: "text-emerald-600",
      icon: TrendingUp,
      suffix: "USDC",
    },

    {
      title: "ROI",
      value:
        analytics?.roi ??
        0,
      color: "text-blue-600",
      icon: BarChart3,
      suffix: "%",
    },

    {
      title: "Unrealized P&L",
      value:
        analytics?.unrealizedPnL ??
        0,
      color: "text-amber-600",
      icon: CircleDollarSign,
      suffix: "USDC",
    },

    {
      title: "Average Entry",
      value:
        analytics?.averageEntryPrice ??
        0,
      color: "text-purple-600",
      icon: Wallet,
      suffix: "USDC",
    },

    {
      title: "YES Positions",
      value:
        analytics?.yesPositions ??
        portfolio?.yesPositions ??
        0,
      color: "text-green-600",
      icon: TrendingUp,
    },

    {
      title: "NO Positions",
      value:
        analytics?.noPositions ??
        portfolio?.noPositions ??
        0,
      color: "text-red-600",
      icon: CircleDollarSign,
    },

    {
      title: "Total Trades",
      value:
        analytics?.totalTrades ??
        portfolio?.totalTrades ??
        0,
      color: "text-sky-600",
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

  const Icon =
    card.icon;

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

                  {

                    card.suffix === "USDC"

                      ? formatCurrency(
                          card.value,
                          ""
                        )

                      : card.suffix === "%"

                        ? formatPercentage(
                            card.value
                          )

                        : formatNumber(
                            card.value
                          )

                  }

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