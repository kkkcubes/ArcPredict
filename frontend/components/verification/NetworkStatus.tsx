"use client";

import { useVerification }
  from "@/hooks/useVerification";

export default function NetworkStatus() {

  const {
    network,
  } = useVerification();

  if (!network) return null;

  return (
    <div className="card p-6">

      <h2 className="text-xl font-bold mb-4">
        Network
      </h2>

      <p>
        {network.network}
      </p>

      <p>
        Chain ID:
        {" "}
        {network.chainId}
      </p>

    </div>
  );
}