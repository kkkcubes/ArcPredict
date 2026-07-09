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
          (yesPool / total) * 100
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
        h-full
        bg-white
        border
        border-gray-200
        rounded-[28px]
        p-5
        sm:p-6
        lg:p-7
        transition-all
duration-300
ease-out
hover:-translate-y-2
hover:scale-[1.01]
hover:shadow-2xl
hover:border-violet-300
      "
    >

      {/* Header */}

      <div
        className="
          flex
          flex-col
          gap-4
        "
      >

        <div
          className="
            flex
            flex-wrap
            items-center
            gap-2
          "
        >

          <span
            className="
              px-3
              py-1
              rounded-full
              bg-violet-100
              text-violet-700
              text-xs
              font-semibold
            "
          >
            {market.category || "General"}
          </span>

          <span
            className={`
              px-3
              py-1
              rounded-full
              text-xs
              font-semibold
              ${
                market.resolved
                  ? "bg-red-100 text-red-600"
                  : "bg-green-100 text-green-600"
              }
            `}
          >
            {market.resolved
              ? "Resolved"
              : "Live"}
          </span>

        </div>

        <h3
          className="
            text-xl
            sm:text-2xl
            font-bold
            text-gray-900
            leading-snug
            break-words
          "
        >
          {market.question}
        </h3>

        <p
  className="
    text-sm
    text-gray-500
  "
>
  Resolution:

  {" "}

  {market.endTime && Number(market.endTime) > 0
  ? new Date(
      Number(market.endTime) * 1000
    ).toLocaleDateString(
      "en-US",
      {
        year: "numeric",
        month: "short",
        day: "numeric",
      }
    )
  : "Unknown"}
</p>

      </div>

      {/* YES / NO */}

      <div className="mt-8">

        <div
          className="
            flex
            items-center
            justify-between
            mb-3
            text-sm
            sm:text-base
          "
        >

          <span
            className="
              font-bold
              text-green-600
            "
          >
            YES ({yesPool})
          </span>

          <span
            className="
              font-bold
              text-red-500
            "
          >
            NO ({noPool})
          </span>

        </div>

        <div
          className="
            h-4
            rounded-full
            bg-gray-200
            overflow-hidden
          "
        >

          <div
            className="
              h-full
              bg-green-500
              transition-all
              duration-500
            "
            style={{
              width: `${yesPercent}%`,
            }}
          />

        </div>

      </div>

      {/* Footer */}

      <div
        className="
          mt-8
          pt-6
          border-t
          border-gray-200
          grid
          grid-cols-1
          sm:grid-cols-3
          gap-5
        "
      >

        <div>

          <p
            className="
              text-xs
              uppercase
              tracking-wide
              text-gray-400
            "
          >
            Volume
          </p>

          <h4
            className="
              mt-2
              text-lg
              font-bold
              text-gray-900
            "
          >
            {market.totalVolume ?? 0} USDC
          </h4>

        </div>

        <div>

          <p
            className="
              text-xs
              uppercase
              tracking-wide
              text-gray-400
            "
          >
            Traders
          </p>

          <h4
            className="
              mt-2
              text-lg
              font-bold
              text-gray-900
            "
          >
            {participants}
          </h4>

        </div>

        <div>

  <p
    className="
      text-xs
      uppercase
      tracking-wide
      text-gray-400
    "
  >
    Market
  </p>

  <h4
    className="
      mt-2
      font-semibold
      text-violet-600
    "
  >
    #{market.marketId}
  </h4>

</div>

      </div>

    </Link>

  );

}