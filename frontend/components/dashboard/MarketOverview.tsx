"use client";

import {
  ArrowUpRight,
} from "lucide-react";

import Link from "next/link";

import { useMarkets } from "@/hooks/useMarkets";

export default function MarketOverview() {

  const { markets } = useMarkets();

  return (

    <section className="dashboard-card p-8">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Markets
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Market Overview
          </h2>

        </div>

      </div>

      <div className="space-y-4">

        {markets.slice(0, 5).map((market: any) => (

          <Link
            key={market.marketId ?? market.id}
            href={`/market/${market.marketId ?? market.id}`}
            className="
              flex
              items-center
              justify-between
              rounded-2xl
              border
              border-gray-200
              p-5
              hover:bg-gray-50
              transition-all
            "
          >

            <div>

              <p className="font-semibold text-gray-900">
                {market.question}
              </p>

              <p className="text-sm text-gray-500 mt-1">
                #{market.marketId ?? market.id}
              </p>

            </div>

            <ArrowUpRight
              size={20}
              className="text-violet-600"
            />

          </Link>

        ))}

      </div>

    </section>

  );

}