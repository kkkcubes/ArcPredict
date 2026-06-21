"use client";

import {
  ResponsiveContainer,
  AreaChart,
  Area,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

import { usePortfolio }
  from "@/hooks/usePortfolio";

export default function TraderGrowthChart() {

  const {
    portfolio,
  } = usePortfolio();

  const trades =
    portfolio?.totalTrades || 0;

  const data = [
    {
      name: "Trades",
      value: trades,
    },
  ];

  return (

    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Trader Activity
      </h2>

      <div className="h-72">

        <ResponsiveContainer
          width="100%"
          height="100%"
        >

          <AreaChart data={data}>

            <XAxis dataKey="name" />

            <YAxis />

            <Tooltip />

            <Area
              type="monotone"
              dataKey="value"
            />

          </AreaChart>

        </ResponsiveContainer>

      </div>

    </div>

  );
}