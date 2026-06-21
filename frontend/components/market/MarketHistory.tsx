"use client";

import {
  useRealtimeEvents,
} from "@/hooks/useRealtimeEvents";

export default function MarketHistory() {

  const {
    events,
  } = useRealtimeEvents();

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-4
        "
      >
        Market History
      </h2>

      <div className="space-y-3">

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
              {event.eventName}
            </div>
          )
        )}

      </div>

    </div>
  );
}