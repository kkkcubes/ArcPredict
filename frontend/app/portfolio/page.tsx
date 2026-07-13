"use client";

import BackButton
  from "@/components/ui/BackButton";

import PortfolioSummary
  from "@/components/portfolio/PortfolioSummary";

export default function PortfolioPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          Portfolio
        </h1>

        <p className="mt-2 text-gray-500">
          View your live portfolio,
          positions, performance,
          and trading activity.
        </p>

      </div>

      <PortfolioSummary />

    </main>

  );

}