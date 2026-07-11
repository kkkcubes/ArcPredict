"use client";

import {
  Layers3,
} from "lucide-react";

import {
  useAnalyticsHistory,
} from "@/hooks/useAnalyticsHistory";

import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function CategoryBreakdown() {

  const {
    history,
    loading,
  } = useAnalyticsHistory();

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

  const categories =
    history?.categoryBreakdown ?? [];

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

        {categories.map(
          (
            category: any
          ) => (

            <div
              key={category.date}
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
                {category.date}
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
                {category.value}
              </span>

            </div>

          )
        )}

      </div>

    </section>

  );

}