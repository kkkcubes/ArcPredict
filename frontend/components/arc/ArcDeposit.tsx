"use client";

import { useState } from "react";

export default function ArcDeposit() {

  const [amount, setAmount] =
    useState("");

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Deposit USDC
      </h2>

      <input
        value={amount}
        onChange={(e) =>
          setAmount(
            e.target.value
          )
        }
        placeholder="Deposit Amount"
        className="
          w-full
          p-3
          rounded-xl
          bg-black
          border
          border-gray-800
        "
      />

      <button
        className="
          mt-4
          bg-green-600
          px-5
          py-3
          rounded-xl
        "
      >
        Deposit
      </button>

    </div>
  );
}