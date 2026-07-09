"use client";

import Link from "next/link";
import { ArrowRight, Plus } from "lucide-react";

export default function HeroSection() {
  return (
    <section
      id="dashboard"
      className="
        dashboard-card
        p-10
        flex
        items-center
        justify-between
        gap-10
      "
    >
      {/* Left */}

      <div className="max-w-2xl">

        <p className="text-violet-600 font-semibold uppercase tracking-widest">
          ArcPredict
        </p>

        <h1 className="mt-4 text-6xl font-black leading-tight text-gray-900">
          Predict.
          <br />
          Trade.
          <br />
          Earn.
        </h1>

        <p className="mt-6 text-lg text-gray-500 leading-8">
          Trade decentralized prediction markets powered by Arc.
          Follow real-time outcomes, analyze trends and make
          informed decisions.
        </p>

        <div className="mt-10 flex gap-4">

          <Link
            href="#markets"
            className="dashboard-button-primary flex items-center gap-2"
          >
            Explore Markets

            <ArrowRight size={18} />
          </Link>

          <Link
            href="/create"
            className="dashboard-button-secondary flex items-center gap-2"
          >
            <Plus size={18} />

            Create Market

          </Link>

        </div>

      </div>

      {/* Right */}

      <div
        className="
          dashboard-card
          w-[360px]
          p-8
        "
      >
        <p className="text-sm text-gray-500">
          Wallet Balance
        </p>

        <h2 className="text-5xl font-black mt-3">
          39.23
        </h2>

        <p className="text-gray-500">
          USDC
        </p>

        <div className="mt-10">

          <p className="text-sm text-gray-400">
            Wallet
          </p>

          <p className="font-semibold mt-2">
            0x7b3A...DE5c
          </p>

        </div>

      </div>

    </section>
  );
}