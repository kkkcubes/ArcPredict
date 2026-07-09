"use client";

import {
  ArrowUpRight,
  Clock,
} from "lucide-react";

import {
  formatDistanceToNow,
} from "date-fns";

import {
  useRealtimeEvents,
} from "@/hooks/useRealtimeEvents";

export default function LatestTransactions() {

  const {
    events,
  } = useRealtimeEvents();

  return (

    <section className="dashboard-card p-8">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Blockchain
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Latest Transactions
          </h2>

        </div>

      </div>

      {events.length === 0 ? (

        <div
          className="
            rounded-2xl
            border
            border-dashed
            border-gray-300
            p-10
            text-center
            text-gray-500
          "
        >
          Waiting for blockchain events...
        </div>

      ) : (

        <div className="space-y-4">

          {events
            .slice(0, 10)
            .map(
              (
                event: any,
                index: number
              ) => (

                <div
                  key={index}
                  className="
                    rounded-2xl
                    border
                    border-gray-200
                    p-5
                    hover:bg-gray-50
                    transition-all
                  "
                >

                  <div className="flex justify-between items-start">

                    <div>

                      <h3 className="font-semibold text-gray-900">
                        {event.eventName || "Transaction"}
                      </h3>

                      <p className="text-sm text-gray-500 mt-2">
                        Market #{event.marketId ?? "-"}
                      </p>

                      {event.wallet && (

                        <p className="text-sm text-gray-500 mt-1">

                          {event.wallet.slice(0, 6)}
                          ...
                          {event.wallet.slice(-4)}

                        </p>

                      )}

                    </div>

                    <ArrowUpRight
                      size={18}
                      className="text-violet-600"
                    />

                  </div>

                  <div className="flex items-center gap-2 mt-4 text-sm text-gray-500">

                    <Clock size={15} />

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

      )}

    </section>

  );

}