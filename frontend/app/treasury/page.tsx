"use client";

import BackButton from "@/components/ui/BackButton";

import TreasuryOverview
  from "@/components/dashboard/TreasuryOverview";

import {
  useDashboardStats,
} from "@/hooks/useDashboardStats";

export default function TreasuryPage() {

  const {
    stats,
  } = useDashboardStats();

  return (

    <main className="container py-10">

      <BackButton />

      <TreasuryOverview
        stats={stats}
      />

    </main>

  );

}