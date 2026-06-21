"use client";

import { useArcBalance }
  from "@/hooks/useArcBalance";

export default function ArcUnifiedBalance() {

  const {
    vaultBalance,
    liquidity,
  } = useArcBalance();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Unified Balance
      </h2>

      <div className="space-y-4">

        <div>

          <p>Vault Balance</p>

          <p className="text-3xl font-bold">
            {vaultBalance}
          </p>

        </div>

        <div>

          <p>Liquidity</p>

          <p className="text-3xl font-bold">
            {liquidity}
          </p>

        </div>

      </div>

    </div>
  );
}