"use client";

import { useMarkets }
  from "@/hooks/useMarkets";

export default function MarketOverview() {

  const {
    markets,
  } = useMarkets();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Market Overview
      </h2>

      <div className="space-y-3">

        {markets.slice(0, 5).map(
          (market: any) => (

            <div
              key={market.marketId ?? market.id}
              className="
                flex
                justify-between
                border-b
                border-gray-800
                pb-2
              "
            >
              <span>
                {market.question}
              </span>

              <span>
  #{market.marketId ?? market.id}
</span>

            </div>
          )
        )}

      </div>

    </div>
  );
}