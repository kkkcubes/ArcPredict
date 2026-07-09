"use client";

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

        <SectionHeader
          eyebrow="Community"
          title="Top Traders"
          subtitle="Monitor the most active traders and recent on-chain activity."
        />

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