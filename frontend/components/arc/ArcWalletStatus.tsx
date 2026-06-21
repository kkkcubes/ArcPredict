"use client";

import { useAccount } from "wagmi";

export default function ArcWalletStatus() {

  const {
    address,
    isConnected,
  } = useAccount();

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Wallet Status
      </h2>

      <p>
        Connected:
        {" "}
        {isConnected
          ? "Yes"
          : "No"}
      </p>

      <p className="break-all">
        {address}
      </p>

    </div>
  );
}