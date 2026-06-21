"use client";

import Link from "next/link";
import { useMarkets } from "@/hooks/useMarkets";

export default function TrendingMarkets() {

  const {
    markets,
    loading,
  } = useMarkets();

  if (loading) {
    return <div>Loading markets...</div>;
  }

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Trending Markets
      </h2>

      <div className="space-y-4">

        {markets.map(
          (market: any) => (

            <Link
              key={market.marketId ?? market.id}
              href={`/market/${market.marketId ?? market.id}`}
              className="
                block
                border
                border-gray-800
                rounded-xl
                p-4
              "
            >
              <h3 className="font-semibold">
                {market.question}
              </h3>

              <p className="text-gray-400">
                {market.category}
              </p>
            </Link>
          )
        )}

      </div>
    </div>
  );
}