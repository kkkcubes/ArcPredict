"use client";

import {
  Activity,
  Wallet,
  ArrowUpRight,
  BarChart3,
  CheckCircle2,
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
  className={`
    inline-flex
    items-center
    rounded-full
    px-3
    py-1
    text-xs
    font-bold
    uppercase
    tracking-wide
    ${
      event.eventType === "SHARES_PURCHASED"
        ? "bg-green-100 text-green-700"
        : event.eventType === "MARKET_CREATED"
        ? "bg-blue-100 text-blue-700"
        : event.eventType === "MARKET_RESOLVED"
        ? "bg-purple-100 text-purple-700"
        : "bg-gray-100 text-gray-700"
    }
  `}
>
  {event.eventType?.replace("_", " ")}
</span>

<span className="font-semibold text-gray-900">
  {
  event.eventType === "SHARES_PURCHASED"
    ? `Bought ${event.position ?? "-"} shares in Market #${event.marketId}`
    : event.eventType === "MARKET_CREATED"
    ? "New prediction market created"
    : event.eventType === "MARKET_RESOLVED"
    ? `Market #${event.marketId} has been resolved`
    : event.summary ?? event.eventType
}
</span>

                  </div>

                  <p className="mt-3 text-gray-500">
                    Market #{event.marketId}
                  </p>

                </div>

                {event.eventType === "SHARES_PURCHASED" ? (

  <Activity
    size={22}
    className="text-green-600"
  />

) : event.eventType === "MARKET_CREATED" ? (

  <BarChart3
    size={22}
    className="text-blue-600"
  />

) : event.eventType === "MARKET_RESOLVED" ? (

  <CheckCircle2
    size={22}
    className="text-purple-600"
  />

) : (

  <ArrowUpRight
    size={22}
    className="text-gray-500"
  />

)}

              </div>
{event.eventType === "SHARES_PURCHASED" && (

  <div
    className="
      mt-5
      grid
      grid-cols-1
      md:grid-cols-3
      gap-4
    "
  >

    <div>

      <p className="text-xs text-gray-500">
        Amount
      </p>

      <p className="font-bold text-lg">
        {event.amount ?? 0} USDC
      </p>

    </div>

    <div>

      <p className="text-xs text-gray-500">
        Position
      </p>

      <p className="font-bold">
        {event.position ?? "-"}
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
            ? `${event.wallet.slice(0, 6)}...${event.wallet.slice(-4)}`
            : "Unknown"}

        </span>

      </div>

    </div>

  </div>

)}

{event.eventType === "MARKET_CREATED" && (

  <div className="mt-5">

    <p className="text-xs text-gray-500">
      Market
    </p>

    <p className="font-bold">
      #{event.marketId}
    </p>

  </div>

)}

{event.eventType === "MARKET_RESOLVED" && (

  <div className="mt-5">

    <p className="text-xs text-gray-500">
      Market
    </p>

    <p className="font-bold">
      #{event.marketId}
    </p>

  </div>

)}

              <div
  className="
    mt-5
    border-t
    border-gray-100
    pt-4
    flex
    flex-col
    gap-3
    sm:flex-row
    sm:items-center
    sm:justify-between
  "
>

  <span
    className="
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
  </span>

  {event.txHash && (

    <div
      className="
        flex
        items-center
        gap-3
      "
    >

      <code
        className="
          rounded-lg
          bg-gray-100
          px-2
          py-1
          text-xs
          text-gray-700
        "
      >
        {`${event.txHash.slice(0, 8)}...${event.txHash.slice(-6)}`}
      </code>

      <a
        href={`https://explorer.testnet.arc.xyz/tx/${event.txHash}`}
        target="_blank"
        rel="noopener noreferrer"
        className="
          text-sm
          font-medium
          text-violet-600
          hover:text-violet-700
        "
      >
        View Transaction
      </a>

    </div>

  )}

</div>

            </div>

          )
        )}

      </div>

    </section>

  );

}