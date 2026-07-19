"use client";

import {
  TrendingUp,
} from "lucide-react";

import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

import {
  useAnalyticsContext,
} from "@/providers/AnalyticsProvider";

export default function SentimentChart() {

  const {
    analytics,
    loading,
  } = useAnalyticsContext();

  if (loading) {

    return (

      <LoadingSkeleton
        className="
          h-[280px]
          md:h-[360px]
          rounded-3xl
          bg-white
          border
          border-gray-200
        "
      />

    );

  }

  const bullish =
  analytics?.bullishPercentage ?? 0;

const bearish =
  analytics?.bearishPercentage ?? 0;

  return (

    <section className="dashboard-card p-8 h-full">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Analytics
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Market Sentiment
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-green-100
            flex
            items-center
            justify-center
          "
        >

          <TrendingUp
            size={26}
            className="text-green-600"
          />

        </div>

      </div>

      <div className="space-y-6">

        <div>

          <div className="flex justify-between mb-2">

            <span className="font-medium">
              Bullish
            </span>

            <span>
              {bullish.toFixed(2)}%
            </span>

          </div>

          <div className="h-3 rounded-full bg-gray-200">

            <div
              className="h-3 rounded-full bg-green-500"
              style={{
                width: `${bullish}%`,
              }}
            />

          </div>

        </div>

        <div>

          <div className="flex justify-between mb-2">

            <span className="font-medium">
              Bearish
            </span>

            <span>
              {bearish.toFixed(2)}%
            </span>

          </div>

          <div className="h-3 rounded-full bg-gray-200">

            <div
              className="h-3 rounded-full bg-red-500"
              style={{
                width: `${bearish}%`,
              }}
            />

          </div>

        </div>

      </div>

    </section>

  );

}