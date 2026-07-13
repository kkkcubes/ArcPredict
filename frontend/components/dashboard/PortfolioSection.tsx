"use client";

import Link from "next/link";

import Card from "@/components/ui/Card";
import SectionHeader from "@/components/ui/SectionHeader";

import PortfolioSummary from "@/components/portfolio/PortfolioSummary";
import PnLChart from "@/components/portfolio/PnLChart";
import PositionTable from "@/components/portfolio/PositionTable";
import RewardsPanel from "@/components/portfolio/RewardsPanel";

export default function PortfolioSection() {

  return (

    <section
      id="portfolio"
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
            eyebrow="Portfolio"
            title="Portfolio Overview"
            subtitle="Track your prediction positions, rewards, and trading performance."
          />

          <Link
            href="/portfolio"
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

        <div className="space-y-8">

          <PortfolioSummary />

          <div
            className="
              grid
              grid-cols-1
              xl:grid-cols-2
              gap-6
            "
          >

            <PnLChart />

            <RewardsPanel />

          </div>

          <PositionTable />

        </div>

      </Card>

    </section>

  );

}