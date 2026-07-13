"use client";

import Link from "next/link";
import { Search, SlidersHorizontal } from "lucide-react";
import { useState } from "react";

import { useMarkets } from "@/hooks/useMarkets";
import MarketCard from "@/components/market/MarketCard";

export default function TrendingMarkets() {
  const {
    markets,
    loading,
  } = useMarkets();

  const [search, setSearch] =
  useState("");

  const [category, setCategory] =
  useState("All");

  const [status, setStatus] =
  useState("All");

  if (loading) {
    return (
      <div className="dashboard-card p-8">
        Loading Markets...
      </div>
    );
  }

  const filteredMarkets =
  markets.filter((market: any) => {

    const matchesSearch =
      market.question
        ?.toLowerCase()
        .includes(
          search.toLowerCase()
        );

    const matchesCategory =
      category === "All" ||
      market.category === category;

    const matchesStatus =
      status === "All" ||
      (
        status === "Active" &&
        !market.resolved
      ) ||
      (
        status === "Resolved" &&
        market.resolved
      );

    return (
      matchesSearch &&
      matchesCategory &&
      matchesStatus
    );

  });

  return (
    <section
      id="markets"
      className="dashboard-card p-8 mt-8"
    >

      {/* Header */}

      <div className="flex items-center justify-between mb-8">

        <div>

          <h2 className="text-3xl font-bold text-gray-900">
            Prediction Markets
          </h2>

          <p className="text-gray-500 mt-2">
            Explore live prediction markets powered by ArcPredict.
          </p>

        </div>

        <Link
  href="/markets"
  className="
    text-violet-600
    font-semibold
    hover:underline
  "
>
  View More →
</Link>

      </div>

      {/* Search */}

      <div className="flex gap-4 mb-8">

        <div
          className="
            flex-1
            flex
            items-center
            gap-3
            rounded-2xl
            border
            border-gray-200
            px-5
            py-3
          "
        >

          <Search
            size={18}
            className="text-gray-400"
          />

          <input
  placeholder="Search prediction markets..."
  value={search}
  onChange={(e) =>
    setSearch(e.target.value)
  }
  className="
    flex-1
    outline-none
    bg-transparent
  "
/>

        </div>

        <div
  className="
    flex
    items-center
    gap-2
  "
>

  <SlidersHorizontal
    size={18}
    className="text-gray-500"
  />

  <select
    value={category}
    onChange={(e) =>
      setCategory(
        e.target.value
      )
    }
    className="
      rounded-xl
      border
      border-gray-300
      bg-white
      px-4
      py-3
      text-gray-700
      focus:outline-none
      focus:ring-2
      focus:ring-violet-500
    "
  >

    <option value="All">
      All
    </option>

    <option value="Crypto">
      Crypto
    </option>

    <option value="AI">
      AI
    </option>

    <option value="DeFi">
      DeFi
    </option>

    <option value="Arc Ecosystem">
      Arc Ecosystem
    </option>

  </select>

</div>

      </div>

      <div
  className="
    flex
    items-center
    gap-2
  "
>

  <select
    value={status}
    onChange={(e) =>
      setStatus(
        e.target.value
      )
    }
    className="
      rounded-xl
      border
      border-gray-300
      bg-white
      px-4
      py-3
      text-gray-700
      focus:outline-none
      focus:ring-2
      focus:ring-violet-500
    "
  >

    <option value="All">
      All Status
    </option>

    <option value="Active">
      Active
    </option>

    <option value="Resolved">
      Resolved
    </option>

  </select>

</div>

      {/* Cards */}

      <div
  className="
    grid
    grid-cols-1
    xl:grid-cols-2
    gap-6
  "
>

  {filteredMarkets.map((market: any) => (

    <MarketCard
      key={market.marketId ?? market.id}
      market={market}
    />

  ))}

  {filteredMarkets.length === 0 && (

    <div
      className="
        col-span-full
        text-center
        py-12
      "
    >

      <h3
        className="
          text-xl
          font-semibold
        "
      >
        No prediction markets found
      </h3>

      <p
        className="
          text-gray-500
          mt-2
        "
      >
        Try changing your search.
      </p>

    </div>

  )}

</div>

    </section>
  );
}