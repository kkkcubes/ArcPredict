"use client";

import { useVerification }
  from "@/hooks/useVerification";

export default function LatestBlock() {

  const {
    network,
  } = useVerification();

  return (
    <div className="card p-6">

      <h2 className="text-xl font-bold mb-4">
        Latest Block
      </h2>

      <p className="text-4xl font-bold">
        {network?.latestBlock}
      </p>

    </div>
  );
}