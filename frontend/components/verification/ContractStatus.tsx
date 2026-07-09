"use client";

import {
  ShieldCheck,
} from "lucide-react";

import { useVerification } from "@/hooks/useVerification";

export default function ContractStatus() {

  const { contracts } = useVerification();

  if (!contracts) {
    return (
      <div className="dashboard-card p-8">
        Loading contract status...
      </div>
    );
  }

  return (
    <section
      id="verification"
      className="dashboard-card p-8"
    >
      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Verification
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Smart Contracts
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-green-100
            flex
            items-center
            justify-center
          "
        >
          <ShieldCheck
            size={26}
            className="text-green-600"
          />
        </div>

      </div>

      <div className="space-y-5">

        {Object.entries(contracts).map(
          ([name]) => (

            <div
              key={name}
              className="
                rounded-2xl
                border
                border-gray-200
                p-5
              "
            >

              <div className="flex items-center justify-between">

                <h3 className="font-semibold text-gray-900">
                  {name}
                </h3>

                <span
                  className="
                    rounded-full
                    bg-green-100
                    px-3
                    py-1
                    text-xs
                    font-semibold
                    text-green-700
                  "
                >
                  ✓ Verified
                </span>

              </div>

            </div>

          )
        )}

      </div>

    </section>
  );

}