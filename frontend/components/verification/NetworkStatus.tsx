"use client";

import {
  Globe,
  CheckCircle2,
} from "lucide-react";

import { useVerification } from "@/hooks/useVerification";

export default function NetworkStatus() {

  const {
    network,
  } = useVerification();

  if (!network) {
    return (
      <div className="dashboard-card p-8">
        Loading network...
      </div>
    );
  }

  return (

    <section className="dashboard-card p-8">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Network
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Network Status
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-blue-100
            flex
            items-center
            justify-center
          "
        >
          <Globe
            size={26}
            className="text-blue-600"
          />
        </div>

      </div>

      <div className="space-y-5">

        <div
          className="
            rounded-2xl
            border
            border-gray-200
            p-5
          "
        >

          <div className="flex items-center justify-between">

            <div>

              <p className="text-sm text-gray-500">
                Connected Network
              </p>

              <h3 className="mt-2 text-xl font-bold text-gray-900">
                {network.network}
              </h3>

            </div>

            <CheckCircle2
              size={28}
              className="text-green-500"
            />

          </div>

        </div>

        <div
          className="
            rounded-2xl
            border
            border-gray-200
            p-5
          "
        >

          <p className="text-sm text-gray-500">
            Chain ID
          </p>

          <h3 className="mt-2 text-2xl font-bold text-violet-600">
            {network.chainId}
          </h3>

        </div>

      </div>

    </section>

  );
}