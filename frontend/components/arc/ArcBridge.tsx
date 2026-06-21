"use client";

import { useState } from "react";
import { useArcBridge } from "@/hooks/useArcBridge";

export default function ArcBridge() {

  const [amount, setAmount] =
    useState("");

  const {
    bridgeUSDC,
    loading,
  } = useArcBridge();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Arc Bridge
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
          border-gray-800
        "
      />

      <button
        onClick={() =>
          bridgeUSDC(amount)
        }
        className="
          mt-4
          bg-blue-600
          px-5
          py-3
          rounded-xl
        "
      >
        {loading
          ? "Processing..."
          : "Bridge"}
      </button>

    </div>
  );
}