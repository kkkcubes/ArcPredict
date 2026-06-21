"use client";

import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

import { useMarkets }
  from "@/hooks/useMarkets";

export default function MarketGrowthChart() {

  const { markets } =
    useMarkets();

  const data =
    markets.map(
      (market, index) => ({
        name:
          `#${market.marketId}`,
        value:
          index + 1,
      })
    );

  return (

    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Market Growth
      </h2>

      <div className="h-72">

        <ResponsiveContainer
          width="100%"
          height="100%"
        >

          <LineChart data={data}>

            <XAxis dataKey="name" />

            <YAxis />

            <Tooltip />

            <Line
              type="monotone"
              dataKey="value"
            />

          </LineChart>

        </ResponsiveContainer>

      </div>

    </div>

  );
}