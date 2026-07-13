"use client";

import BackButton
  from "@/components/ui/BackButton";

import TrendingMarkets
  from "@/components/dashboard/TrendingMarkets";

export default function MyMarketsPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          My Markets
        </h1>

        <p className="mt-2 text-gray-500">
          Browse prediction markets you've created
          and participated in.
        </p>

      </div>

      <TrendingMarkets />

    </main>

  );

}