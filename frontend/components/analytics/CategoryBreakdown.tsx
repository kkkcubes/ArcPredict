"use client";

import { useMarkets } from "@/hooks/useMarkets";

export default function CategoryBreakdown() {

  const { markets } =
    useMarkets();

  const categories:
    Record<string, number> = {};

  markets.forEach(
    (market: any) => {

      const category =
        market.category ||
        "Unknown";

      categories[
        category
      ] =
        (categories[
          category
        ] || 0) + 1;
    }
  );

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Category Breakdown
      </h2>

      <div className="space-y-3">

        {Object.entries(
          categories
        ).map(
          (
            [category, count]
          ) => (

            <div
              key={category}
              className="
                flex
                justify-between
                border-b
                border-gray-800
                pb-2
              "
            >
              <span>
                {category}
              </span>

              <span>
                {count}
              </span>

            </div>
          )
        )}

      </div>

    </div>
  );
}