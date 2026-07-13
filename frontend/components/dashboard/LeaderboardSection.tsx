"use client";

import Link from "next/link";

import Card from "@/components/ui/Card";
import SectionHeader from "@/components/ui/SectionHeader";

import LeaderboardWidget from "@/components/dashboard/LeaderboardWidget";
import LiveActivityFeed from "@/components/dashboard/LiveActivityFeed";

export default function LeaderboardSection() {

  return (

    <section
      id="leaderboard"
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
            eyebrow="Community"
            title="Top Traders"
            subtitle="Monitor the most active traders and recent on-chain activity."
          />

          <Link
            href="/leaderboard"
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

          <LeaderboardWidget />

          <LiveActivityFeed />

        </div>

      </Card>

    </section>

  );

}