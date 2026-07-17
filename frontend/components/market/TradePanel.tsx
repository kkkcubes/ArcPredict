"use client";

import {
  useState,
} from "react";

import {
  toast,
} from "react-hot-toast";

import {
  useTrade,
} from "@/hooks/useTrade";

interface Props {
  marketId: number;
}

export default function TradePanel({
  marketId,
}: Props) {

  const [amount, setAmount] =
    useState("");

 const {
  buyYes: submitBuyYes,
  buyNo: submitBuyNo,
  isPending,
} = useTrade();

  const buyYes =
    async () => {

      const value =
        Number(amount);

      if (
        !Number.isFinite(value) ||
        value <= 0
      ) {

        toast.error(
          "Enter a valid amount"
        );

        return;

      }

      await submitBuyYes(
  marketId,
  value
);

setAmount("");

    };

  const buyNo =
    async () => {

      const value =
        Number(amount);

      if (
        !Number.isFinite(value) ||
        value <= 0
      ) {

        toast.error(
          "Enter a valid amount"
        );

        return;

      }

     await submitBuyNo(
  marketId,
  value
);

setAmount("");

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
  disabled={isPending}
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
  disabled={isPending}
          className="
  bg-green-600
  px-6
  py-3
  rounded-xl
"
        >
          {isPending
  ? "Submitting..."
  : "Buy YES"}
        </button>

        <button
  onClick={buyNo}
  disabled={isPending}
          className="
  bg-green-600
  px-6
  py-3
  rounded-xl
"
        >
          {isPending
  ? "Submitting..."
  : "Buy NO"}
        </button>

      </div>

    </div>

  );

}