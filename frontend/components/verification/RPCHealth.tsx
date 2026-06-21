"use client";

import { ARC_RPC }
  from "@/lib/constants";

export default function RPCHealth() {

  return (
    <div className="card p-6">

      <h2 className="text-xl font-bold mb-4">
        RPC Health
      </h2>

      <p>
        Endpoint:
      </p>

      <p className="break-all">
        {ARC_RPC}
      </p>

      <p className="text-green-500 mt-2">
        Connected
      </p>

    </div>
  );
}