"use client";

import { useState } from "react";

import MarketCard from "@/components/market/MarketCard";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";
import { useMarkets } from "@/hooks/useMarkets";
import Hero from "@/components/dashboard/Hero";
import KPICards from "@/components/dashboard/KPICards";
import ProtocolHealth
  from "@/components/dashboard/ProtocolHealth";
import VerificationSection from "@/components/dashboard/VerificationSection";
import PortfolioSection from "@/components/dashboard/PortfolioSection";
import LeaderboardSection from "@/components/dashboard/LeaderboardSection";
import RecentTradesSection from "@/components/dashboard/RecentTradesSection";
import AnalyticsSection from "@/components/dashboard/AnalyticsSection";
import { useTradesData } from "@/hooks/useTradesData";
import { useSystemMetrics } from "@/hooks/useSystemMetrics";
import EmptyState from "@/components/ui/EmptyState";
import DashboardLayout
  from "@/components/layout/DashboardLayout";

export default function HomePage() {

  const {
    markets,
    loading: marketsLoading,
    refresh: refreshMarkets,
  } = useMarkets();

  const {
    trades,
    loading: tradesLoading,
    error: tradesError,
  } = useTradesData();

  const {
    metrics,
    loading: metricsLoading,
    refresh: refreshMetrics,
  } = useSystemMetrics();

  const [search, setSearch] =
    useState("");

  const [category, setCategory] =
    useState("All");

  const filteredMarkets =
    markets.filter(
      (m) => {

        const matchesSearch =
          m.question
            ?.toLowerCase()
            .includes(
              search.toLowerCase()
            );

        const matchesCategory =
          category === "All"
            ? true
            : m.category === category;

        return (
          matchesSearch &&
          matchesCategory
        );

      }
    );

  return (

    <DashboardLayout>

      <main
        id="top"
        className="container"
      >

        <section
          id="top"
          className="animate-fade-up"
        >
          <Hero />
        </section>

        <div className="animate-fade-up">
          <KPICards />
        </div>

        <div className="animate-fade-up">
          <ProtocolHealth
            stats={metrics}
            loading={metricsLoading}
            refresh={refreshMetrics}
          />
        </div>

        <section
          id="verification"
          className="animate-fade-up"
        >
          <VerificationSection />
        </section>

        <section
          id="markets"
          className="
            animate-fade-up
            dashboard-card
            p-8
            mt-8
            mb-8
          "
        >

          <div className="flex items-center justify-between mb-8">

            <div>

              <h2 className="text-3xl font-bold text-gray-900">
                Prediction Markets
              </h2>

              <p className="text-gray-500 mt-2">
                Explore live prediction markets powered by ArcPredict.
              </p>

            </div>

            <button
              className="dashboard-button-secondary"
            >
              View All
            </button>

          </div>

          <div className="relative mb-6">

            <input
              value={search}
              onChange={(e) =>
                setSearch(
                  e.target.value
                )
              }
              placeholder="Search prediction markets..."
              className="
                w-full
                h-14
                rounded-2xl
                border
                border-gray-200
                bg-white
                pl-14
                pr-5
                text-gray-900
                placeholder:text-gray-400
                outline-none
                focus:border-violet-500
                focus:ring-4
                focus:ring-violet-100
                transition-all
              "
            />

            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="absolute left-5 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M21 21l-4.35-4.35M17 10a7 7 0 11-14 0 7 7 0 0114 0z"
              />
            </svg>

          </div>

          <div className="flex flex-wrap gap-3 mb-8">

            {[
              "All",
              "Crypto",
              "DeFi",
              "AI",
              "Arc Ecosystem",
            ].map(
              (item) => (

                <button
                  key={item}
                  onClick={() =>
                    setCategory(item)
                  }
                  className={`
                    px-5
                    py-2.5
                    rounded-full
                    text-sm
                    font-semibold
                    transition-all
                    duration-300
                    ${
                      category === item
                        ? "bg-[#6D4AFF] text-white shadow-lg"
                        : "bg-white border border-gray-200 text-gray-600 hover:bg-gray-50"
                    }
                  `}
                >
                  {item}
                </button>

              )
            )}

          </div>

          {marketsLoading ? (

            <div
              className="
                grid
                grid-cols-1
                xl:grid-cols-2
                gap-6
                mt-8
              "
            >

              {Array.from({ length: 4 }).map((_, index) => (

                <LoadingSkeleton
                  key={index}
                  className="
                    h-[320px]
                    rounded-3xl
                    bg-white
                    border
                    border-gray-200
                  "
                />

              ))}

            </div>

          ) : filteredMarkets.length === 0 ? (

            <EmptyState
              title="No Markets Found"
              description="Try changing your search or category filters."
            />

          ) : (

            <div
              className="
                grid
                grid-cols-1
                xl:grid-cols-2
                gap-6
                mt-8
              "
            >

              {filteredMarkets.map((market) => (

                <MarketCard
                  key={market.marketId}
                  market={{
                    marketId: market.marketId,
                    question: market.question,
                    category: market.category,
                    yesPool: market.yesPool,
                    noPool: market.noPool,
                    participants: market.participants,
                    endTime: market.endTime,
                    totalVolume: market.totalVolume,
                    resolved: market.resolved,
                  }}
                />

              ))}

            </div>

          )}

        </section>

        <section
          id="leaderboard"
          className="animate-fade-up"
        >
          <LeaderboardSection />
        </section>

        <section
          id="analytics"
          className="animate-fade-up"
        >
          <AnalyticsSection />
        </section>

        <section
          id="portfolio"
          className="animate-fade-up"
        >
          <PortfolioSection />
        </section>

        <div className="animate-fade-up">

            <RecentTradesSection
    trades={trades}
    loading={tradesLoading}
/>
          
        </div>

      </main>

    </DashboardLayout>

  );

}