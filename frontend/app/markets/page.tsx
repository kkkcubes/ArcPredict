"use client";

import BackButton from "@/components/ui/BackButton";
import TrendingMarkets from "@/components/dashboard/TrendingMarkets";

export default function MarketsPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <TrendingMarkets />

    </main>

  );

}