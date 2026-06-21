"use client";

import { useAnalytics }
  from "@/hooks/useAnalytics";

export default function MarketSentiment() {

  const {
    analytics,
  } = useAnalytics();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Market Sentiment
      </h2>

      <div className="space-y-4">

        <div>

          <p>
            Bullish
          </p>

          <div className="w-full bg-gray-800 rounded-full h-4">

            <div
              className="
                bg-green-500
                h-4
                rounded-full
              "
              style={{
                width:
                  `${analytics?.bullish || 0}%`
              }}
            />

          </div>

        </div>

        <div>

          <p>
            Bearish
          </p>

          <div className="w-full bg-gray-800 rounded-full h-4">

            <div
              className="
                bg-red-500
                h-4
                rounded-full
              "
              style={{
                width:
                  `${analytics?.bearish || 0}%`
              }}
            />

          </div>

        </div>

      </div>

    </div>
  );
}