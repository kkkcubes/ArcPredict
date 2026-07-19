"use client";

import Link from "next/link";
import CountUp from "react-countup";

type Props = {
  stats: any;
};

export default function TreasuryOverview({
  stats,
}: Props) {

  const utilization =
    Number(
      stats?.treasuryUtilization ?? 0
    );

  const progressColor =
    utilization < 50
      ? "bg-green-500"
      : utilization < 80
      ? "bg-yellow-500"
      : "bg-red-500";

  const healthColor =
    stats?.treasuryHealth === "Healthy"
      ? "text-green-600"
      : stats?.treasuryHealth === "Warning"
      ? "text-yellow-500"
      : "text-red-600";

  return (

    <section
      className="
        card
        p-6
        mb-6
      "
    >

      <div
        className="
          flex
          items-center
          justify-between
          mb-6
        "
      >

        <h2
          className="
            text-2xl
            font-bold
          "
        >
          Treasury Overview
        </h2>

        <Link
          href="/treasury"
          className="
            text-violet-600
            font-semibold
            hover:underline
          "
        >
          View More →
        </Link>

      </div>

      <div
        className="
          grid
          grid-cols-1
          md:grid-cols-2
          xl:grid-cols-4
          gap-6
        "
      >

        <div>
          <p className="text-gray-400">
            Vault Balance
          </p>

          <h3 className="text-3xl font-bold mt-2">
            <CountUp
              end={stats?.vaultBalance ?? 0}
              duration={1.5}
              separator=","
            />
            {" "}USDC
          </h3>
        </div>

        <div>
          <p className="text-gray-400">
            Total Liquidity
          </p>

          <h3 className="text-3xl font-bold mt-2">
            <CountUp
              end={stats?.totalLiquidity ?? 0}
              duration={1.5}
              separator=","
            />
            {" "}USDC
          </h3>
        </div>

        <div>
          <p className="text-gray-400">
            Locked Liquidity
          </p>

          <h3 className="text-3xl font-bold mt-2">
            <CountUp
              end={stats?.totalLockedLiquidity ?? 0}
              duration={1.5}
              separator=","
            />
            {" "}USDC
          </h3>
        </div>

        <div>
          <p className="text-gray-400">
            Released Liquidity
          </p>

          <h3 className="text-3xl font-bold mt-2">
            <CountUp
              end={stats?.totalReleasedLiquidity ?? 0}
              duration={1.5}
              separator=","
            />
            {" "}USDC
          </h3>
        </div>

        <div>
          <p className="text-gray-400">
            Available Liquidity
          </p>

          <h3 className="text-3xl font-bold mt-2">
            <CountUp
              end={stats?.availableLiquidity ?? 0}
              duration={1.5}
              separator=","
            />
            {" "}USDC
          </h3>
        </div>

        <div>
          <p className="text-gray-400">
            Treasury Utilization
          </p>

          <h3 className="text-3xl font-bold mt-2">
            {utilization.toFixed(1)}%
          </h3>

          <div className="mt-4">

            <div className="w-full h-3 rounded-full bg-gray-200">

              <div
                className={`h-3 rounded-full ${progressColor}`}
                style={{
                  width: `${Math.min(
                    utilization,
                    100
                  )}%`,
                }}
              />

            </div>

          </div>

        </div>

        <div>
          <p className="text-gray-400">
            Treasury Health
          </p>

          <h3
            className={`text-3xl font-bold mt-2 ${healthColor}`}
          >
            {stats?.treasuryHealth ?? "Unknown"}
          </h3>
        </div>

      </div>

    </section>

  );

}