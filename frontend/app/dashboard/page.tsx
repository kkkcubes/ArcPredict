"use client";

import Link from "next/link";

import {
  useBackendRealtime,
} from "@/hooks/useBackendRealtime";

import {
  useDashboardStats
} from "@/hooks/useDashboardStats";

import {
  useBackendMarkets,
} from "@/hooks/useBackendMarkets";

import LiveActivityFeed
  from "@/components/dashboard/LiveActivityFeed";

import ContractInfo
  from "@/components/dashboard/ContractInfo";  

import {
  useRpcHealth,
} from "@/hooks/useRpcHealth";  

export default function DashboardPage() {

    console.log("Dashboard mounted");

  const {
    stats,
    setStats,
  } = useDashboardStats();

  const {
  rpc,
} = useRpcHealth();

  const {
    markets,
    setMarkets,
  } = useBackendMarkets();

  console.log("Calling useBackendRealtime");

  useBackendRealtime(

    (market) => {

      setMarkets(
        (previous: any[]) => {

          const exists =
            previous.find(
              (m) =>
                m.marketId ===
                market.marketId
            );

          if (exists) {

            return previous.map(
              (m) =>
                m.marketId ===
                market.marketId
                  ? market
                  : m
            );

          }

          return [
            market,
            ...previous,
          ];

        }
      );

      setStats(
        (previous: any) => {

          if (!previous) {
            return previous;
          }

          return {
            ...previous,
            activeMarkets:
              previous.activeMarkets + 1,
          };
        }
      );

    },

    (event) => {

      setStats(
        (previous: any) => {

          if (!previous) {
            return previous;
          }

          return {
    ...previous,
    totalVolume:
        previous.totalVolume +
        Number(event.amount || 0),

    totalTrades:
        previous.totalTrades + 1,
};
        }
      );

    },

    () => {}

  );

  return (
    <main className="container">

      <div className="flex items-center justify-between mb-8">

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

      <section className="card p-6 mb-6">

        <h2 className="text-xl font-semibold mb-4">
          Arc Network Verification
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
  {rpc?.chainId ?? "-"}
</p>
          </div>

          <ContractInfo />

          <div>
  <p className="text-gray-400">
    RPC Status
  </p>

  <p
    className={
      rpc?.connected
        ? "text-green-500"
        : "text-red-500"
    }
  >
    {rpc?.connected
      ? "Connected"
      : "Disconnected"}
  </p>
</div>

<div>
  <p className="text-gray-400">
    RPC Latency
  </p>

  <p>
    {rpc?.latency ?? 0} ms
  </p>
</div>

<div>
  <p className="text-gray-400">
    Latest RPC Block
  </p>

  <p>
    {rpc?.latestBlock ?? 0}
  </p>
</div>

        </div>

      </section>

      <section className="grid grid-cols-4 gap-4 mb-6">

        <div className="card p-5">
          <p className="text-gray-400">
            Active Markets
          </p>

          <h3 className="text-3xl font-bold mt-2">
            {stats?.activeMarkets ?? 0}
          </h3>
        </div>

        <div className="card p-5">
          <p className="text-gray-400">
            Total Volume
          </p>

          <h3 className="text-3xl font-bold mt-2">
            {stats?.totalVolume ?? 0} USDC
          </h3>
        </div>

        <div className="card p-5">
  <p className="text-gray-400">
    Total Trades
  </p>

  <h3 className="text-3xl font-bold mt-2">
    {stats?.totalTrades ?? 0}
  </h3>
</div>

        <div className="card p-5">
  <p className="text-gray-400">
    Latest Block
  </p>

  <h3 className="text-3xl font-bold mt-2">
    {stats?.latestBlock ?? 0}
  </h3>
</div>

      </section>

      <section className="card p-6 mb-6">

        <div className="flex items-center justify-between mb-4">

          <h2 className="text-xl font-semibold">
            Active Markets
          </h2>

        </div>

        <div className="space-y-3">

          {markets
            ?.slice(0, 5)
            .map(
              (market: any) => (

                <div
                  key={market.marketId}
                  className="
                    border
                    border-gray-800
                    rounded-xl
                    p-4
                  "
                >

                  <div className="flex justify-between">

                    <div>

                      <h3 className="font-semibold">
                        {market.question}
                      </h3>

                      <p className="text-gray-400 text-sm">
                        {market.category}
                      </p>

                    </div>

                    <div className="text-right">

                      <p>
                        Volume:{" "}
                        {market.totalVolume}
                      </p>

                      <p>
                        Users:{" "}
                        {market.participants}
                      </p>

                    </div>

                  </div>

                </div>

              )
            )}

        </div>

      </section>

      <LiveActivityFeed />

    </main>
  );
}