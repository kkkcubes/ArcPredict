"use client";

import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
} from "recharts";

import {
  TrendingUp,
} from "lucide-react";

import {
  useMarkets,
} from "@/hooks/useMarkets";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function MarketGrowthChart() {
 
  const {
  markets,
  loading,
} = useMarkets();

if (loading) {
  return (
    <LoadingSkeleton
      className="
        h-[280px] md:h-[360px]
        rounded-3xl
        bg-white
        border
        border-gray-200
      "
    />
  );
}

  const data = markets.map((market, index) => ({
    name: `#${market.marketId}`,
    value: index + 1,
  }));

  return (
    <section className="dashboard-card p-8 h-full">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Analytics
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Market Growth
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-violet-100
            flex
            items-center
            justify-center
          "
        >
          <TrendingUp
            size={26}
            className="text-[#6D4AFF]"
          />
        </div>

      </div>

      <div className="h-[260px] md:h-[320px]">

        <ResponsiveContainer
          width="100%"
          height="100%"
        >

          <LineChart data={data}>

            <CartesianGrid
              strokeDasharray="3 3"
              vertical={false}
            />

            <XAxis
              dataKey="name"
              tickLine={false}
              axisLine={false}
            />

            <YAxis
              tickLine={false}
              axisLine={false}
            />

            <Tooltip />

            <Line
              type="monotone"
              dataKey="value"
              stroke="#6D4AFF"
              strokeWidth={4}
              dot={{
                r: 5,
              }}
              activeDot={{
                r: 7,
              }}
            />

          </LineChart>

        </ResponsiveContainer>

      </div>

    </section>
  );
}