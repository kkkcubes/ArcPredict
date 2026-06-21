"use client";

import Link from "next/link";

interface Props {
  market: any;
}

export default function MarketCard({
  market,
}: Props) {

  const yesPool =
    Number(
      market.yesPool || 0
    );

  const noPool =
    Number(
      market.noPool || 0
    );

  const total =
    yesPool + noPool;

  const yesPercent =
    total === 0
      ? 50
      : Math.round(
          (yesPool / total) *
            100
        );

  const noPercent =
    100 - yesPercent;

  const participants =
    Number(
      market.participants || 0
    );

  return (

    <Link
      href={`/market/${market.marketId}`}
      className="
        block
        card
        p-6
        cursor-pointer
        transition-all
        duration-300
        hover:scale-[1.02]
        hover:border-blue-500
        hover:shadow-xl
      "
    >

      <div
        className="
          flex
          items-start
          justify-between
          mb-4
        "
      >

        <div>

          <h3
            className="
              text-xl
              font-bold
              mb-2
            "
          >
            {market.question}
          </h3>

          <p
            className="
              text-gray-400
            "
          >
            {market.category}
          </p>

        </div>

        <span
  className={`
    px-3
    py-1
    rounded-full
    text-xs
    font-medium
    border
    ${
      market.resolved
        ? `
          bg-red-500/10
          text-red-400
          border-red-500/20
        `
        : `
          bg-green-500/10
          text-green-400
          border-green-500/20
        `
    }
  `}
>

  {market.resolved
    ? "Resolved"
    : "Active"}

</span>

      </div>

      <div className="mb-5">

        <div
          className="
            flex
            justify-between
            mb-2
          "
        >

          <span
            className="
              text-green-500
              font-semibold
            "
          >
            YES {yesPercent}%
          </span>

          <span
            className="
              text-red-500
              font-semibold
            "
          >
            NO {noPercent}%
          </span>

        </div>

        <div
          className="
            h-3
            bg-gray-800
            rounded-full
            overflow-hidden
          "
        >

          <div
            className="
              bg-green-500
              h-full
            "
            style={{
              width:
                `${yesPercent}%`,
            }}
          />

        </div>

      </div>

      <div
        className="
          grid
          grid-cols-2
          gap-4
          pt-4
          border-t
          border-gray-800
        "
      >

        <div>

          <p
            className="
              text-xs
              text-gray-500
              uppercase
            "
          >
            Volume
          </p>

          <p
  className="
    text-lg
    font-semibold
    mt-1
  "
>
  {market.totalVolume ?? 0}
</p>

        </div>

        <div>

          <p
            className="
              text-xs
              text-gray-500
              uppercase
            "
          >
            Participants
          </p>

          <p
            className="
              text-lg
              font-semibold
              mt-1
            "
          >
            {participants}
          </p>

        </div>

      </div>

    </Link>

  );
}