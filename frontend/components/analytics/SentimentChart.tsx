"use client";

import { useAnalytics } from "@/hooks/useAnalytics";

export default function SentimentChart() {

  const { analytics } =
    useAnalytics();

  const bullish =
    analytics?.bullish || 0;

  const bearish =
    analytics?.bearish || 0;

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Market Sentiment
      </h2>

      <div className="space-y-4">

        <div>

          <div className="flex justify-between">

            <span>
              Bullish
            </span>

            <span>
              {bullish.toFixed(2)}%
            </span>

          </div>

          <div
            className="
              h-4
              bg-gray-800
              rounded-full
              mt-2
            "
          >
            <div
              className="
                h-4
                bg-green-500
                rounded-full
              "
              style={{
                width:
                  `${bullish}%`,
              }}
            />
          </div>

        </div>

        <div>

          <div className="flex justify-between">

            <span>
              Bearish
            </span>

            <span>
              {bearish.toFixed(2)}%
            </span>

          </div>

          <div
            className="
              h-4
              bg-gray-800
              rounded-full
              mt-2
            "
          >
            <div
              className="
                h-4
                bg-red-500
                rounded-full
              "
              style={{
                width:
                  `${bearish}%`,
              }}
            />
          </div>

        </div>

      </div>

    </div>
  );
}