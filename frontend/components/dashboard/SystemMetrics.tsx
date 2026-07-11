"use client";

import {
  Activity,
  Database,
  Wifi,
  Clock,
  BarChart3,
  Radio,
} from "lucide-react";

import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

import { useSystemMetrics } from "@/hooks/useSystemMetrics";

export default function SystemMetrics() {

  const {
    metrics,
    loading,
  } = useSystemMetrics();

  if (loading) {

    return (

      <LoadingSkeleton
        className="
          h-[420px]
          rounded-3xl
          bg-white
          border
          border-gray-200
        "
      />

    );

  }

  return (

    <section className="dashboard-card p-8">

      <div className="mb-8">

        <p className="text-sm text-gray-500">
          Infrastructure
        </p>

        <h2 className="text-3xl font-bold text-gray-900 mt-1">
          System Metrics
        </h2>

      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

        <MetricCard
          icon={<Wifi size={20} />}
          title="RPC Status"
          value={
            metrics?.rpcConnected
              ? "Connected"
              : "Disconnected"
          }
        />

        <MetricCard
          icon={<Database size={20} />}
          title="Latest Block"
          value={metrics?.latestBlock ?? 0}
        />

        <MetricCard
          icon={<Clock size={20} />}
          title="RPC Latency"
          value={`${metrics?.rpcLatency ?? 0} ms`}
        />

        <MetricCard
          icon={<BarChart3 size={20} />}
          title="Markets"
          value={metrics?.totalMarkets ?? 0}
        />

        <MetricCard
          icon={<Activity size={20} />}
          title="Trades"
          value={metrics?.totalTrades ?? 0}
        />

        <MetricCard
          icon={<Radio size={20} />}
          title="Events"
          value={metrics?.totalEvents ?? 0}
        />

        <MetricCard
  icon={<Activity size={20} />}
  title="Backend Uptime"
  value={`${metrics?.uptimeSeconds ?? 0} sec`}
/>

<MetricCard
  icon={<Database size={20} />}
  title="Memory Usage"
  value={`${metrics?.usedMemoryMb ?? 0} MB`}
/>

<MetricCard
  icon={<Wifi size={20} />}
  title="Database"
  value={metrics?.databaseStatus ?? "-"}
/>

      </div>

    </section>

  );

}

type MetricCardProps = {

  icon: React.ReactNode;

  title: string;

  value: React.ReactNode;

};

function MetricCard({

  icon,

  title,

  value,

}: MetricCardProps) {

  return (

    <div
      className="
        border
        border-gray-200
        rounded-2xl
        p-5
      "
    >

      <div className="flex items-center gap-3 mb-3">

        <div className="text-violet-600">

          {icon}

        </div>

        <h3 className="font-semibold text-gray-900">

          {title}

        </h3>

      </div>

      <p className="text-2xl font-bold text-gray-900">

        {value}

      </p>

    </div>

  );

}