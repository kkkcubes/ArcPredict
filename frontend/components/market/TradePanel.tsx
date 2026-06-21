"use client";

import {
  useState,
} from "react";

interface Props {
  marketId: number;
}

export default function TradePanel({
  marketId,
}: Props) {

  const [amount, setAmount] =
    useState("");

  const buyYes =
    async () => {

      console.log(
        "Buy YES",
        marketId,
        amount
      );
    };

  const buyNo =
    async () => {

      console.log(
        "Buy NO",
        marketId,
        amount
      );
    };

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-4
        "
      >
        Trade
      </h2>

      <input
        value={amount}
        onChange={(e) =>
          setAmount(
            e.target.value
          )
        }
        placeholder="USDC Amount"
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
          flex
          gap-3
        "
      >

        <button
          onClick={buyYes}
          className="
            bg-green-600
            px-6
            py-3
            rounded-xl
          "
        >
          Buy YES
        </button>

        <button
          onClick={buyNo}
          className="
            bg-red-600
            px-6
            py-3
            rounded-xl
          "
        >
          Buy NO
        </button>

      </div>

    </div>
  );
}