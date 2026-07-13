"use client";

import BackButton from "@/components/ui/BackButton";
import LiveActivityFeed from "@/components/dashboard/LiveActivityFeed";

export default function ActivityPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <LiveActivityFeed />

    </main>

  );

}