"use client";

import { useAnalytics }
  from "@/hooks/useAnalytics";

import VolumeGrowthChart
  from "@/components/analytics/VolumeGrowthChart";

import MarketGrowthChart
  from "@/components/analytics/MarketGrowthChart";

import TraderGrowthChart
  from "@/components/analytics/TraderGrowthChart";

export default function AnalyticsPage() {

  const {
    analytics,
    loading,
  } = useAnalytics();

  if (
    loading ||
    !analytics
  ) {

    return (
      <main className="container py-10">
        Loading analytics...
      </main>
    );

  }

  return (

    <main className="container">

      <h1 className="text-4xl font-bold mb-6">
        Analytics
      </h1>

      <section
        className="
          grid
          grid-cols-1
          md:grid-cols-2
          xl:grid-cols-4
          gap-4
        "
      >

        <div className="card p-5">

          <h3 className="text-gray-400">
            Total Markets
          </h3>

          <p className="text-3xl font-bold">
            {analytics.totalMarkets}
          </p>

        </div>

        <div className="card p-5">

          <h3 className="text-gray-400">
            Total Volume
          </h3>

          <p className="text-3xl font-bold">
            {analytics.totalVolume}
          </p>

        </div>

        <div className="card p-5">

          <h3 className="text-gray-400">
            Traders
          </h3>

          <p className="text-3xl font-bold">
            {analytics.totalTraders}
          </p>

        </div>

        <div className="card p-5">

          <h3 className="text-gray-400">
            Open Interest
          </h3>

          <p className="text-3xl font-bold">
            {analytics.openInterest}
          </p>

        </div>

      </section>

      <section
        className="
          grid
          grid-cols-1
          md:grid-cols-2
          gap-4
          mt-6
        "
      >

        <div className="card p-5">

          <h3 className="text-gray-400">
            Bullish
          </h3>

          <p
            className="
              text-3xl
              font-bold
              text-green-400
            "
          >
            {analytics.bullishPercentage.toFixed(2)}%
          </p>

        </div>

        <div className="card p-5">

          <h3 className="text-gray-400">
            Bearish
          </h3>

          <p
            className="
              text-3xl
              font-bold
              text-red-400
            "
          >
            {analytics.bearishPercentage.toFixed(2)}%
          </p>

        </div>

      </section>

      <section
        className="
          grid
          grid-cols-1
          lg:grid-cols-3
          gap-6
          mt-6
        "
      >

        <VolumeGrowthChart />

        <MarketGrowthChart />

        <TraderGrowthChart />

      </section>

    </main>

  );
}