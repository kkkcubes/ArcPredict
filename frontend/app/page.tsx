"use client";

import { useEffect, useState } from "react";

import MarketCard from "@/components/market/MarketCard";
import ConnectWallet from "@/components/ConnectWallet";
import PortfolioSummary from "@/components/portfolio/PortfolioSummary";
import PositionTable from "@/components/portfolio/PositionTable";
import PnLChart from "@/components/portfolio/PnLChart";
import RewardsPanel from "@/components/portfolio/RewardsPanel";
import VolumeGrowthChart from "@/components/analytics/VolumeGrowthChart";
import MarketGrowthChart from "@/components/analytics/MarketGrowthChart";
import TraderGrowthChart from "@/components/analytics/TraderGrowthChart";
import LeaderboardWidget from "@/components/dashboard/LeaderboardWidget"; 
import ContractInfo from "@/components/dashboard/ContractInfo";

import {
  useArcBalance,
} from "@/hooks/useArcBalance";

import { useAnalytics } from "@/hooks/useAnalytics";


export default function HomePage() {
  
  console.log(
    "HOME PAGE RENDERED"
  );

  const [markets, setMarkets] =
    useState<any[]>([]);

  const [trades, setTrades] =
    useState<any[]>([]);

  const [search, setSearch] =
    useState("");

  const [category, setCategory] =
    useState("All");

  const {
  analytics,
} = useAnalytics();

  useEffect(() => {

  console.log(
    "API URL =",
    process.env.NEXT_PUBLIC_API_URL
  );

  fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/api/markets`
  )
    .then((r) => r.json())
    .then((data) => {

  console.log(
    "MARKETS FROM API =",
    data
  );

  console.log(
    "MARKET COUNT =",
    data.length
  );

  setMarkets(data);

})
    .catch((e) => {

      console.error(
        "FAILED TO LOAD MARKETS",
        e
      );

    });

  fetch(
    `${process.env.NEXT_PUBLIC_API_URL}/api/trades`
  )
    .then((r) => r.json())
    .then((data) => {

      setTrades(data);

    })
    .catch(console.error);

}, []);

  const filteredMarkets =
    markets.filter(
      (m) => {

        const matchesSearch =
          m.question
            ?.toLowerCase()
            .includes(
              search
                .toLowerCase()
            );

        const matchesCategory =
          category === "All"
            ? true
            : m.category ===
              category;

        return (
          matchesSearch &&
          matchesCategory
        );
      }
    );
    
    console.log(
  "FILTERED MARKETS =",
  filteredMarkets
    );

  return (
    <main className="container">

      <header
  className="
    mb-10
    rounded-3xl
    border
    border-gray-800
    p-10
    bg-gradient-to-r
    from-blue-950
    to-black
  "
>

  <h1
    className="
      text-6xl
      font-extrabold
      mb-4
    "
  >
    Predict The Future
  </h1>

  <p
    className="
      text-xl
      text-gray-300
      max-w-2xl
    "
  >
    Trade on real-world outcomes
    secured by Arc Network.
  </p>

  <div
    className="
      flex
      flex-wrap
      gap-4
      mt-8
    "
  >

    <a
      href="#markets"
      className="
        px-6
        py-3
        rounded-xl
        bg-blue-600
        hover:bg-blue-700
        transition
      "
    >
      Explore Markets
    </a>

    <a
      href="/create"
      className="
        px-6
        py-3
        rounded-xl
        border
        border-gray-600
        hover:border-blue-500
        transition
      "
    >
      Create Market
    </a>

    <ConnectWallet />

  </div>

</header>

<section
  className="
    grid
    grid-cols-1
    md:grid-cols-3
    gap-4
    mb-6
  "
>

  <a
    href="/create"
    className="
      card
      p-6
      hover:border-blue-500
      transition
    "
  >
    <h3 className="text-xl font-bold">
      Create Market
    </h3>

    <p className="text-gray-400 mt-2">
      Launch a new prediction market
    </p>

  </a>

  <div className="card p-6">

    <h3 className="text-xl font-bold">
      Active Markets
    </h3>

    <p className="text-gray-400 mt-2">
      {markets.length} live markets
    </p>

  </div>

  <div className="card p-6">

    <h3 className="text-xl font-bold">
      Live Trading
    </h3>

    <p className="text-gray-400 mt-2">
      Real-time Arc Network activity
    </p>

  </div>

</section>

      {/* Verification */}

      <section 
      id="markets"
      className="card p-6 mb-6">

        <h2 className="text-xl font-semibold mb-4">
          Network Verification
        </h2>

        <div className="grid grid-cols-2 gap-4">

          <div>
            <p className="text-gray-400">
              Network
            </p>

            <p>
              Arc Testnet
            </p>
          </div>

          <div>
            <p className="text-gray-400">
              Chain ID
            </p>

            <p>
              5042002
            </p>
          </div>

          <div>
            <p className="text-gray-400">
              RPC Status
            </p>

            <p className="text-green-500">
              Connected
            </p>
          </div>

          <div className="col-span-2">
  <ContractInfo />
</div>

        </div>

      </section>


      {/* Dashboard */}

      <section
        className="
          grid
          grid-cols-1
          md:grid-cols-2
          xl:grid-cols-4
          gap-4
          mb-6
        "
      >

        <div className="card p-5">
          <p className="text-gray-400">
            Active Markets
          </p>

          <h3 className="text-3xl font-bold mt-2">
              {analytics?.totalMarkets ?? 0}
          </h3>
        </div>

        <div className="card p-5">
          <p className="text-gray-400">
            Total Volume
          </p>

          <h3 className="text-3xl font-bold mt-2">
  {analytics?.totalVolume ?? 0}
</h3>
        </div>

        <div className="card p-5">
          <p className="text-gray-400">
            Traders
          </p>

          <h3 className="text-3xl font-bold mt-2">
  {analytics?.totalTraders ?? 0}
</h3>
        </div>

        <div className="card p-5">
          <p className="text-gray-400">
            Open Interest
          </p>

          <h3 className="text-3xl font-bold mt-2">
  {analytics?.openInterest ?? 0}
</h3>
        </div>

      </section>

      {/* Markets */}

      <section className="card p-6 mb-6">

        <h2 className="text-xl font-semibold mb-4">
          Markets
        </h2>

        <input
          value={search}
          onChange={(e) =>
            setSearch(
              e.target.value
            )
          }
          placeholder="Search markets..."
          className="
            w-full
            p-3
            rounded-xl
            bg-card
            border
            border-border
            mb-4
          "
        />

        <div
          className="
            flex
            flex-wrap
            gap-2
            mb-4
          "
        >

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
                  px-4
                  py-2
                  rounded-xl
                  border
                  transition
                  ${
                    category === item
                      ? "bg-blue-600 border-blue-600"
                      : "border-gray-700"
                  }
                `}
              >
                {item}
              </button>

            )
          )}

        </div>

        {filteredMarkets.length === 0 ? (

          <div className="text-gray-400">
            No markets found
          </div>

        ) : (

          <div className="grid gap-4">

            {filteredMarkets.map(
  (market) => (

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

  )
)}
          </div>

        )}

      </section>

      <section className="mb-6">

  <h2
    className="
      text-3xl
      font-bold
      mb-6
    "
  >
    Top Traders
  </h2>

  <LeaderboardWidget />

</section>

      <section className="mb-6">

  <h2
    className="
      text-3xl
      font-bold
      mb-6
    "
  >
    Portfolio
  </h2>

  <PortfolioSummary />

  <div
    className="
      grid
      grid-cols-1
      lg:grid-cols-3
      gap-6
      mt-6
    "
  >

    <PnLChart />

    <RewardsPanel />

    <PositionTable />

  </div>

</section>

<section className="mb-6">

  <h2
    className="
      text-3xl
      font-bold
      mb-6
    "
  >
    Analytics
  </h2>

  <div
    className="
      grid
      grid-cols-1
      lg:grid-cols-3
      gap-6
    "
  >

    <VolumeGrowthChart />

    <MarketGrowthChart />

    <TraderGrowthChart />

  </div>

</section>

<section className="mb-6">

  <h2
    className="
      text-3xl
      font-bold
      mb-6
    "
  >
    Leaderboard
  </h2>

  <LeaderboardWidget />

</section>

      {/* Recent Trades */}

<section className="card p-6">

  <h2
    className="
      text-2xl
      font-bold
      mb-6
    "
  >
    Recent Trades
  </h2>

  {trades.length === 0 ? (

    <div className="text-gray-400">
      No trades found
    </div>

  ) : (

    <div className="overflow-x-auto">

      <table
        className="
          w-full
          text-left
        "
      >

        <thead>

          <tr
            className="
              border-b
              border-gray-800
            "
          >

            <th className="py-3">
              Market
            </th>

            <th className="py-3">
              Side
            </th>

            <th className="py-3">
              Amount
            </th>

            <th className="py-3">
              Wallet
            </th>

          </tr>

        </thead>

        <tbody>

          {trades.map(
            (trade) => (

              <tr
                key={`${trade.txHash}-${trade.marketId}`}
                className="
                  border-b
                  border-gray-900
                "
              >

                <td className="py-4">
                  #{trade.marketId}
                </td>

                <td className="py-4">

                  <span
                    className={
                      trade.yesPosition
                        ? "text-green-500"
                        : "text-red-500"
                    }
                  >
                    {trade.yesPosition
                      ? "YES"
                      : "NO"}
                  </span>

                </td>

                <td className="py-4">
                  {trade.amount}
                </td>

                <td className="py-4">

                  {trade.trader?.slice(0, 6)}
                  ...
                  {trade.trader?.slice(-4)}

                </td>

              </tr>

            )
          )}

        </tbody>

      </table>

    </div>

  )}

</section>

    </main>
  );
}