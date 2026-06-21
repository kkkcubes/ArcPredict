"use client";

import {
  useRealtimeEvents,
} from "@/hooks/useRealtimeEvents";

interface Props {
  marketId: number;
}

export default function TradeFeed({
  marketId,
}: Props) {

  const { events } =
    useRealtimeEvents();

  const filtered =
  events
    .filter(
      (event) =>
        Number(event.marketId) ===
        Number(marketId)
    )
    .slice(0, 20);

  return (

    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Recent Activity
      </h2>

      <div className="space-y-4">

        {filtered.length === 0 && (

          <p className="text-gray-400">
            No activity yet
          </p>

        )}

        {filtered.map(
          (
            event: any,
            index: number
          ) => (

            <div
              key={index}
              className="
                border-b
                border-gray-800
                pb-3
              "
            >

              <p className="font-medium">

                {event.wallet
                  ?.slice(0, 6)}
                ...
                {event.wallet
                  ?.slice(-4)}

              </p>

              <p className="text-gray-400">

                {event.eventType}

              </p>

              <p>

                {event.amount}
                {" "}
                USDC

              </p>

            </div>

          )
        )}

      </div>

    </div>

  );
}