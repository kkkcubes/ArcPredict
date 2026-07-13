"use client";

import Link from "next/link";

import Card from "@/components/ui/Card";
import SectionHeader from "@/components/ui/SectionHeader";

import VolumeGrowthChart from "@/components/analytics/VolumeGrowthChart";
import DailyTradesChart from "@/components/analytics/DailyTradesChart";
import MarketGrowthChart from "@/components/analytics/MarketGrowthChart";
import SentimentChart from "@/components/analytics/SentimentChart";
import CategoryBreakdown from "@/components/analytics/CategoryBreakdown";

export default function AnalyticsSection() {

  return (

    <section
      id="analytics"
      className="mb-8"
    >

      <Card className="p-8">

        <div
          className="
            flex
            items-start
            justify-between
            gap-4
            mb-8
          "
        >

          <SectionHeader
            eyebrow="Analytics"
            title="Market Analytics"
            subtitle="Real-time insights generated from live prediction market activity."
          />

          <Link
            href="/analytics"
            className="
              whitespace-nowrap
              text-violet-600
              font-semibold
              hover:underline
            "
          >
            View More →
          </Link>

        </div>

        <div
          className="
            grid
            grid-cols-1
            xl:grid-cols-2
            gap-6
          "
        >

          <VolumeGrowthChart />

          <DailyTradesChart />

          <MarketGrowthChart />

          <SentimentChart />

          <CategoryBreakdown />

        </div>

      </Card>

    </section>

  );

}