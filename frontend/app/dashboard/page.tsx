"use client";

import Link from "next/link";

import {
  useBackendRealtime,
} from "@/hooks/useBackendRealtime";

import {
  useDashboardStats,
} from "@/hooks/useDashboardStats";

import SystemMetrics
  from "@/components/dashboard/SystemMetrics";

import TreasuryOverview
  from "@/components/dashboard/TreasuryOverview";

import TrendingMarkets
  from "@/components/dashboard/TrendingMarkets";

import LeaderboardSection
  from "@/components/dashboard/LeaderboardSection";

import {
  useTradesData,
} from "@/hooks/useTradesData";

import RecentTradesSection
  from "@/components/dashboard/RecentTradesSection";   
  
import PortfolioSection
  from "@/components/dashboard/PortfolioSection";  

import ProtocolHealth
  from "@/components/dashboard/ProtocolHealth";  

export default function DashboardPage() {

  console.log(
    "Dashboard mounted"
  );

  const {
  stats,
  loading,
  refresh,
  setStats,
} = useDashboardStats();

  const {
  trades,
  loading: tradesLoading,
} = useTradesData();

  console.log(
    "Calling useBackendRealtime"
  );

  useBackendRealtime(

    () => {

      refresh();

    },

    async (event) => {

      const amount =
        Number(
          event.amount || 0
        );

      setStats(
        (previous: any) => {

          if (!previous) {
            return previous;
          }

          return {

            ...previous,

            totalVolume:
              previous.totalVolume +
              amount,

            totalTrades:
              previous.totalTrades +
              1,

          };

        }
      );

      refresh();

    },

    () => {}

  );

  return (

    <main className="container">

      <div
        className="
          flex
          items-center
          justify-between
          mb-8
        "
      >

        <div>

          <h1 className="text-5xl font-bold">
            ArcPredict
          </h1>

          <p className="text-gray-400 mt-2">
            Real-Time On-Chain Prediction Markets on Arc
          </p>

        </div>

        <Link
          href="/create"
          className="
            px-5
            py-3
            bg-blue-600
            rounded-xl
            hover:bg-blue-700
          "
        >
          Create Market
        </Link>

      </div>

      <SystemMetrics />

      <ProtocolHealth
  stats={stats}
  loading={loading}
  refresh={refresh}
/>

      <TreasuryOverview
        stats={stats}
      />

      <TrendingMarkets />

      <LeaderboardSection />

      <RecentTradesSection
  trades={trades}
  loading={tradesLoading}
/>

<PortfolioSection />

    </main>

  );

}