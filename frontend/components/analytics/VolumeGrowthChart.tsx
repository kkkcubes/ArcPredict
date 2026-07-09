"use client";

import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

import {
  BarChart3,
} from "lucide-react";

import {
  useAnalyticsContext,
} from "@/providers/AnalyticsProvider";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function VolumeGrowthChart() {
  
  const {
  analytics,
  loading,
} = useAnalyticsContext();

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

  const data = [
    {
      name: "Volume",
      value: Number(analytics?.totalVolume ?? 0),
    },
  ];

  return (
    <section className="dashboard-card p-8 h-full">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Analytics
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Volume Growth
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
          <BarChart3
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

          <BarChart data={data}>

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

            <Bar
              dataKey="value"
              radius={[12, 12, 0, 0]}
              fill="#6D4AFF"
            />

          </BarChart>

        </ResponsiveContainer>

      </div>

    </section>
  );
}