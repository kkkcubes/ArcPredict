"use client";

import {
  useEffect,
  useState,
} from "react";

import {
  stompClient,
} from "@/lib/stomp";
import { useParams } from "next/navigation";
import { useTrade } from "@/hooks/useTrade";

import TradeFeed
  from "@/components/market/TradeFeed";

export default function MarketDetailsPage() {

  const params = useParams();

  const [market, setMarket] =
  useState<any | null | undefined>(
    null
  );

  const [amount, setAmount] =
    useState(1);

  const {
    buyYes,
    buyNo,
    isPending,
  } = useTrade();

  useEffect(() => {

    fetch(
  `${process.env.NEXT_PUBLIC_API_URL}/api/markets`
)

  .then((r) => {

    if (!r.ok) {

      throw new Error(
        "Failed to fetch markets"
      );

    }

    return r.json();

  })
  .then((markets) => {

    const found =
      markets.find(
        (m: any) =>
          String(m.marketId) ===
          String(params.id)
      );

    setMarket(
      found
    );

  })
  .catch((error) => {

    console.error(
      error
    );

    setMarket(
      undefined
    );

  });

  }, [params.id]);

  useEffect(() => {

  const subscribe = () => {

    stompClient.subscribe(
      "/topic/markets",
      (message) => {

        const updatedMarket =
          JSON.parse(
            message.body
          );

        if (
          String(
            updatedMarket.marketId
          ) ===
          String(params.id)
        ) {

          setMarket(
            updatedMarket
          );

        }

      }
    );

  };

  if (
    stompClient.connected
  ) {

    subscribe();

  } else {

    stompClient.onConnect =
      subscribe;

  }

}, [params.id]);

  if (market === null) {

  return (
    <main className="container py-10">
      Loading market...
    </main>
  );

}

if (market === undefined) {

  return (
    <main className="container py-10">
      Market not found.
    </main>
  );

}

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

  return (

    <main className="container py-10">

      <div
        className="
          card
          p-8
          rounded-2xl
          shadow-lg
          mb-6
        "
      >

        <div
          className="
            flex
            items-start
            justify-between
            mb-6
          "
        >

          <div>

            <span
              className="
                text-sm
                text-gray-400
              "
            >
              Market #{market.marketId}
            </span>

            <h1
              className="
                text-4xl
                font-bold
                mt-2
              "
            >
              {market.question}
            </h1>

            <p
              className="
                text-gray-400
                mt-2
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

        <div
          className="
            grid
            grid-cols-2
            md:grid-cols-3
            gap-4
            mb-8
          "
        >

          <div>

  <p className="text-gray-500">
    Creator
  </p>

  <p
    className="
      text-sm
      font-bold
    "
  >
    {market.creator
      ? `${market.creator.slice(0, 6)}...${market.creator.slice(-4)}`
      : "Unknown"}
  </p>

</div>

<div>

  <p className="text-gray-500">
    Open Interest
  </p>

  <p
    className="
      text-2xl
      font-bold
    "
  >
    {yesPool + noPool}
  </p>

</div>

          <div>

            <p className="text-gray-500">
              Volume
            </p>

            <p
              className="
                text-2xl
                font-bold
              "
            >
              {market.totalVolume ?? 0}
            </p>

          </div>

          <div>

            <p className="text-gray-500">
              Participants
            </p>

            <p
              className="
                text-2xl
                font-bold
              "
            >
              {market.participants}
            </p>

          </div>

          <div>

            <p className="text-gray-500">
              YES Pool
            </p>

            <p
              className="
                text-2xl
                font-bold
                text-green-500
              "
            >
              {yesPool}
            </p>

          </div>

          <div>

            <p className="text-gray-500">
              NO Pool
            </p>

            <p
              className="
                text-2xl
                font-bold
                text-red-500
              "
            >
              {noPool}
            </p>

          </div>

        </div>

        <div className="mb-8">

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
              h-4
              bg-gray-800
              rounded-full
              overflow-hidden
            "
          >

            <div
              className="
                h-full
                bg-green-500
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
            border-t
            border-gray-800
            pt-6
          "
        >

          <label
            className="
              block
              mb-2
              text-gray-400
            "
          >
            Trade Amount
          </label>

          <input
            type="number"
            min={1}
            value={amount}
            onChange={(e) =>
              setAmount(
                Number(
                  e.target.value
                )
              )
            }
            className="
              w-full
              p-3
              rounded-xl
              bg-black
              border
              border-gray-700
              mb-4
            "
          />

          <div
            className="
              grid
              grid-cols-1
              md:grid-cols-2
              gap-4
            "
          >

            <button
              onClick={() =>
                buyYes(
                  market.marketId,
                  amount
                )
              }
              disabled={
  isPending ||
  market.resolved
}
              className="
                bg-green-600
                hover:bg-green-700
                transition
                py-3
                rounded-xl
                font-semibold
              "
            >
              Buy YES
            </button>

            <button
              onClick={() =>
                buyNo(
                  market.marketId,
                  amount
                )
              }
              disabled={
  isPending ||
  market.resolved
}
              className="
                bg-red-600
                hover:bg-red-700
                transition
                py-3
                rounded-xl
                font-semibold
              "
            >
              Buy NO
            </button>

          </div>

        </div>

      </div>

      <TradeFeed
  marketId={market.marketId}
/>

    </main>

  );
}