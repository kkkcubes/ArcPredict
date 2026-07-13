"use client";

import Link from "next/link";

import ConnectWallet
  from "@/components/ConnectWallet";

export default function Hero() {
  return (
    <section
      className="
        relative
        overflow-hidden
        rounded-[32px]
        bg-gradient-to-br
        from-[#4F46E5]
        via-[#6D4AFF]
        to-[#8B5CF6]
        p-12
        text-white
        mb-8
      "
    >
      <div className="max-w-3xl">

        <p className="uppercase tracking-[0.25em] text-white/80 font-semibold">
          ArcPredict
        </p>

        <h1 className="mt-5 text-6xl font-black leading-tight">
          Predict.
          <br />
          Trade.
          <br />
          Earn.
        </h1>

        <p className="mt-6 max-w-2xl text-lg text-white/80 leading-8">
          Trade decentralized prediction markets with
          real-time data, analytics and AI-powered insights.
        </p>

        <div className="mt-10 flex flex-wrap gap-4">

          <Link
  href="/markets"
  className="
    px-7
    py-4
    rounded-2xl
    bg-white
    text-[#4F46E5]
    font-semibold
    hover:scale-105
    transition
  "
>
  Explore Markets
</Link>

          <Link
            href="/create"
            className="
              px-7
              py-4
              rounded-2xl
              border
              border-white/30
              hover:bg-white/10
              transition
            "
          >
            Create Market
          </Link>

          <ConnectWallet />

        </div>

      </div>

    </section>
  );
}