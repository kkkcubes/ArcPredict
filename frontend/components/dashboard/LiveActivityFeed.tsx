"use client";

import {
  Activity,
  Wallet,
  ArrowUpRight,
} from "lucide-react";

import {
  formatDistanceToNow,
} from "date-fns";

import {
  useRealtimeEvents,
} from "@/hooks/useRealtimeEvents";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";
import EmptyState from "@/components/ui/EmptyState";

export default function LiveActivityFeed() {

  const {
  events,
  loading,
} = useRealtimeEvents();

if (loading) {
  return (
    <LoadingSkeleton
      className="
        h-[520px]
        rounded-3xl
        bg-white
        border
        border-gray-200
      "
    />
  );
}

  return (

    <section
  className="
    dashboard-card
    p-5
    md:p-8
  "
>

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Real-Time
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Live Activity
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
          <Activity
            size={26}
            className="text-[#6D4AFF]"
          />
        </div>

      </div>

      <div className="space-y-5">

        {events.length === 0 && (

          <EmptyState
  title="No Live Activity"
  description="Waiting for live trading activity..."
/>

        )}

        {events.map(
          (
            event: any,
            index: number
          ) => (

            <div
              key={index}
              className="
  rounded-3xl
  border
  border-gray-200
  p-5
  transition-all
  duration-300
  ease-out
  hover:-translate-y-1
  hover:shadow-xl
  hover:border-violet-200
"
            >

              <div className="flex items-start justify-between">

                <div>

                  <div className="flex items-center gap-3">

                    <span
                      className="
  inline-flex
  items-center
  rounded-full
  bg-green-100
  px-3
  py-1
  text-xs
  font-bold
  uppercase
  tracking-wide
  text-green-700
"
                    >
                      TRADE
                    </span>

                    <span className="font-semibold text-gray-900">
                      Trade Executed
                    </span>

                  </div>

                  <p className="mt-3 text-gray-500">
                    Market #{event.marketId}
                  </p>

                </div>

                <ArrowUpRight
                  size={20}
                  className="text-violet-600"
                />

              </div>

              <div
  className="
    mt-5
    grid
    grid-cols-1
    sm:grid-cols-2
    gap-4
  "
>

                <div>

                  <p className="text-xs text-gray-500">
                    Amount
                  </p>

                  <p className="font-bold text-lg">
                    {event.amount} USDC
                  </p>

                </div>

                <div>

                  <p className="text-xs text-gray-500">
                    Wallet
                  </p>

                  <div className="flex items-center gap-2">

                    <Wallet
                      size={16}
                      className="text-gray-400"
                    />

                    <span className="font-medium">

                      {event.wallet
                        ? `${event.wallet.slice(0,6)}...${event.wallet.slice(-4)}`
                        : "Unknown"}

                    </span>

                  </div>

                </div>

              </div>

              <div
  className="
    mt-5
    border-t
    border-gray-100
    pt-4
    text-sm
    text-gray-500
  "
>

                {event.timestamp
                  ? formatDistanceToNow(
                      new Date(event.timestamp),
                      {
                        addSuffix: true,
                      }
                    )
                  : "Just now"}

              </div>

            </div>

          )
        )}

      </div>

    </section>

  );

}