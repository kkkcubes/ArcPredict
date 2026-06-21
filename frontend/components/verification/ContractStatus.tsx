"use client";

import { useVerification }
  from "@/hooks/useVerification";

export default function ContractStatus() {

  const {
    contracts,
  } = useVerification();

  if (!contracts) return null;

  return (
    <div className="card p-6">

      <h2 className="text-xl font-bold mb-4">
        Contracts
      </h2>

      <div className="space-y-2">

        {Object.entries(
          contracts
        ).map(
          ([key, value]) => (

            <div
              key={key}
              className="break-all"
            >
              <strong>
                {key}
              </strong>
              :
              {" "}
              {String(value)}
            </div>
          )
        )}

      </div>

    </div>
  );
}