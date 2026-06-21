"use client";

import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

import { useAnalytics }
  from "@/hooks/useAnalytics";

export default function VolumeGrowthChart() {

  const { analytics } =
    useAnalytics();

  const data = [
    {
      name: "Volume",
      value:
        analytics?.totalVolume || 0,
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
        Volume Growth
      </h2>

      <div className="h-72">

        <ResponsiveContainer
          width="100%"
          height="100%"
        >

          <BarChart data={data}>

            <XAxis dataKey="name" />

            <YAxis />

            <Tooltip />

            <Bar dataKey="value" />

          </BarChart>

        </ResponsiveContainer>

      </div>

    </div>

  );
}