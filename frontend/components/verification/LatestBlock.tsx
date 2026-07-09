"use client";

import {
  Blocks,
  ArrowUpRight,
} from "lucide-react";

import { useVerification } from "@/hooks/useVerification";

export default function LatestBlock() {

  const {
    network,
  } = useVerification();

  if (!network) {
    return (
      <div className="dashboard-card p-8">
        Loading latest block...
      </div>
    );
  }

  return (

    <section className="dashboard-card p-8">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Blockchain
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Latest Block
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-indigo-100
            flex
            items-center
            justify-center
          "
        >
          <Blocks
            size={26}
            className="text-indigo-600"
          />
        </div>

      </div>

      <div
        className="
          rounded-3xl
          border
          border-gray-200
          bg-gradient-to-br
          from-indigo-50
          to-white
          p-8
        "
      >

        <div className="flex items-center justify-between">

          <div>

            <p className="text-sm text-gray-500">
              Current Block Height
            </p>

            <h3
              className="
                mt-3
                text-5xl
                font-black
                text-indigo-600
              "
            >
              {network.latestBlock ?? "-"}
            </h3>

          </div>

          <ArrowUpRight
            size={34}
            className="text-indigo-500"
          />

        </div>

      </div>

    </section>

  );

}