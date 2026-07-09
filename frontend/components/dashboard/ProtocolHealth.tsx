"use client";

import { useState } from "react";

import { Database, Wallet } from "lucide-react";
import { toast } from "react-hot-toast";
import { useArcBalance } from "@/hooks/useArcBalance";

export default function ProtocolHealth() {
  const {
    vaultBalance,
    liquidity,
    loading,
    refresh,
  } = useArcBalance();

  const [lastUpdated, setLastUpdated] =
  useState(new Date());

  return (
    <section className="bg-white rounded-3xl border border-gray-200 p-6 mb-8">

      <div className="flex items-center justify-between mb-6">

        <div>
          <h2 className="text-2xl font-bold text-gray-900">
            Protocol Health
          </h2>

          <p className="text-gray-500 mt-1">
            Live protocol liquidity statistics.
          </p>
        </div>

        <button
  disabled={loading}
  onClick={async () => {

  await refresh();

  setLastUpdated(
    new Date()
  );

  toast.success(
    "Protocol data refreshed"
  );

}}

  
          className="
  rounded-xl
  bg-violet-600
  hover:bg-violet-700
  disabled:opacity-50
  disabled:cursor-not-allowed
  px-4
  py-2
  text-white
  text-sm
  font-semibold
  transition
"
          
        >
          {loading
  ? "Refreshing..."
  : "Refresh"}
                </button>

      </div>

      <p
        className="
          mb-6
          text-sm
          text-gray-500
        "
      >
        Last Updated{" "}
        {lastUpdated.toLocaleTimeString()}
      </p>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

        <div className="border rounded-2xl p-5 border-gray-200">

          <div className="flex items-center gap-3 mb-3">

            <Database
              className="text-violet-600"
              size={22}
            />

            <h3 className="font-semibold text-gray-900">
              Vault Balance
            </h3>

          </div>

          <p className="text-3xl font-bold text-gray-900">
            {loading
  ? "Loading..."
  : `${Number(vaultBalance).toLocaleString()} USDC`}
          </p>

        </div>

        <div className="border rounded-2xl p-5 border-gray-200">

          <div className="flex items-center gap-3 mb-3">

            <Wallet
              className="text-green-600"
              size={22}
            />

            <h3 className="font-semibold text-gray-900">
              Available Liquidity
            </h3>

          </div>

          <p className="text-3xl font-bold text-gray-900">
            {loading
  ? "Loading..."
  : `${Number(liquidity).toLocaleString()} USDC`}
          </p>

        </div>

      </div>

    </section>
  );
}