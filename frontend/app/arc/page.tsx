"use client";

import Link from "next/link";

import BackButton from "@/components/ui/BackButton";

export default function ArcPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          Arc Finance
        </h1>

        <p className="mt-2 text-gray-500">
          Access Arc ecosystem services including Bridge,
          Deposits, and Unified Balance.
        </p>

      </div>

      <div
        className="
          grid
          grid-cols-1
          md:grid-cols-3
          gap-6
        "
      >

        <Link
          href="/arc/unified-balance"
          className="
            dashboard-card
            p-6
            transition-all
            duration-300
            hover:-translate-y-1
            hover:shadow-xl
          "
        >

          <h2 className="text-2xl font-bold">
            Unified Balance
          </h2>

          <p className="mt-2 text-gray-500">
            View your combined Arc account balance.
          </p>

        </Link>

        <Link
          href="/arc/bridge"
          className="
            dashboard-card
            p-6
            transition-all
            duration-300
            hover:-translate-y-1
            hover:shadow-xl
          "
        >

          <h2 className="text-2xl font-bold">
            Arc Bridge
          </h2>

          <p className="mt-2 text-gray-500">
            Transfer assets across supported networks.
          </p>

        </Link>

        <Link
          href="/arc/deposit"
          className="
            dashboard-card
            p-6
            transition-all
            duration-300
            hover:-translate-y-1
            hover:shadow-xl
          "
        >

          <h2 className="text-2xl font-bold">
            Arc Deposits
          </h2>

          <p className="mt-2 text-gray-500">
            Deposit funds into the Arc ecosystem.
          </p>

        </Link>

      </div>

    </main>

  );

}