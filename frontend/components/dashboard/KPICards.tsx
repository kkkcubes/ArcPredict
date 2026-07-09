"use client";

import {
  TrendingUp,
  BarChart3,
  Users,
  Droplets,
  ShieldCheck,
} from "lucide-react";

import { useAnalytics } from "@/hooks/useAnalytics";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";
import CountUp from "react-countup";

export default function KPICards() {
  const { analytics, loading } = useAnalytics();

  if (loading) {
  return (
    <section
      className="
        grid
        grid-cols-1
        md:grid-cols-5
        gap-5
        mb-8
      "
    >
      {Array.from({ length: 5 }).map((_, index) => (
        <LoadingSkeleton
          key={index}
          className="
            h-28
            rounded-3xl
            bg-white
            border
            border-gray-200
          "
        />
      ))}
    </section>
  );
}

  const cards = [
    {
      title: "Total Volume",
      value: analytics?.totalVolume ?? 0,
      icon: TrendingUp,
      color: "text-violet-600",
    },
    {
      title: "Total Markets",
      value: analytics?.totalMarkets ?? 0,
      icon: BarChart3,
      color: "text-blue-600",
    },
    {
      title: "YES Pool",
      value: analytics?.yesPool ?? 0,
      icon: Users,
      color: "text-green-600",
    },
    {
      title: "NO Pool",
      value: analytics?.noPool ?? 0,
      icon: Droplets,
      color: "text-red-500",
    },
    {
      title: "Network",
      value: "Live",
      icon: ShieldCheck,
      color: "text-violet-600",
    },
  ];

  return (
    <section className="grid grid-cols-1 md:grid-cols-5 gap-5 mb-8">
      {cards.map((card) => {
        const Icon = card.icon;

        return (
          <div
            key={card.title}
            className="
              bg-white
              rounded-3xl
              border
              border-gray-200
              p-6
              shadow-sm
              hover:shadow-lg
              transition
            "
          >
            <div className="flex items-center justify-between">
              <div
                className="
                  w-12
                  h-12
                  rounded-2xl
                  bg-violet-50
                  flex
                  items-center
                  justify-center
                "
              >
                <Icon className={card.color} size={22} />
              </div>
            </div>

            <p className="mt-5 text-sm text-gray-500">
              {card.title}
            </p>

            <h2 className="mt-2 text-3xl font-bold text-gray-900">

  {typeof card.value === "number" ? (

    <CountUp
      end={card.value}
      duration={1.5}
      separator=","
    />

  ) : (

    card.value

  )}

</h2>
          </div>
        );
      })}
    </section>
  );
}