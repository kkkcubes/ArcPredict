"use client";

import {
  formatDistanceToNow,
} from "date-fns";

import { useRealtimeEvents }
  from "@/hooks/useRealtimeEvents";

export default function LiveActivityFeed() {

  const { events } =
    useRealtimeEvents();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Live Activity Feed
      </h2>

      <div className="space-y-3">

        {events.length === 0 && (
          <p>
            Waiting for events...
          </p>
        )}

        {events.map(
          (
            event: any,
            index: number
          ) => (

            <div
              key={index}
              className="
                border-b
                border-gray-800
                pb-2
              "
            >

              <div className="flex items-center gap-2">

                <span
                  className="
                    px-2
                    py-1
                    text-xs
                    rounded-full
                    bg-green-600
                  "
                >
                  TRADE
                </span>

                <p className="font-medium">
                  Trade Executed
                </p>

              </div>

              <p className="text-sm text-gray-400">
                Market #{event.marketId}
              </p>

              <p className="text-sm">
                {event.amount} USDC
              </p>

              <p className="text-xs text-gray-500">

                {event.wallet
                  ? `${event.wallet.slice(0, 6)}...${event.wallet.slice(-4)}`
                  : "Unknown Wallet"}

              </p>

              <p className="text-xs text-gray-500">

                {event.timestamp
                  ? formatDistanceToNow(
                      new Date(event.timestamp),
                      {
                        addSuffix: true,
                      }
                    )
                  : ""}

              </p>

            </div>
          )
        )}

      </div>

    </div>
  );
}