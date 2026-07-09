"use client";

import {
  Layers3,
} from "lucide-react";

import {
  useMarkets,
} from "@/hooks/useMarkets";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function CategoryBreakdown() {

  const {
  markets,
  loading,
} = useMarkets();

if (loading) {
  return (
    <LoadingSkeleton
      className="
        h-[280px] md:h-[360px]
        rounded-3xl
        bg-white
        border
        border-gray-200
      "
    />
  );
}

  const categories: Record<string, number> = {};

  markets.forEach((market: any) => {

    const category = market.category || "Unknown";

    categories[category] =
      (categories[category] || 0) + 1;

  });

  return (

    <section className="dashboard-card p-8 h-full">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Analytics
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Category Breakdown
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
          <Layers3
            size={26}
            className="text-[#6D4AFF]"
          />
        </div>

      </div>

      <div className="space-y-4">

        {Object.entries(categories).map(
          ([category, count]) => (

            <div
              key={category}
              className="
                flex
                items-center
                justify-between
                rounded-2xl
                border
                border-gray-200
                px-5
                py-4
                hover:bg-gray-50
                transition-colors
              "
            >

              <span className="font-semibold text-gray-900">
                {category}
              </span>

              <span
                className="
                  px-3
                  py-1
                  rounded-full
                  bg-violet-100
                  text-violet-700
                  font-semibold
                "
              >
                {count}
              </span>

            </div>

          )
        )}

      </div>

    </section>

  );
}