"use client";

import {
  Copy,
  ExternalLink,
  ShieldCheck,
} from "lucide-react";

export default function ContractInfo() {

  const predictionMarket =
    process.env.NEXT_PUBLIC_PREDICTION_MARKET || "";

  const treasury =
    process.env.NEXT_PUBLIC_MARKET_TREASURY || "";

  const vault =
    process.env.NEXT_PUBLIC_USDC_VAULT || "";

  const explorer =
    process.env.NEXT_PUBLIC_EXPLORER || "";

  const copy = async (value: string) => {
    await navigator.clipboard.writeText(value);
  };

  const cards = [
    {
      title: "Prediction Market",
      address: predictionMarket,
    },
    {
      title: "Treasury",
      address: treasury,
    },
    {
      title: "Vault",
      address: vault,
    },
  ];

  return (

    <section
      id="contracts"
      className="dashboard-card p-8"
    >

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Smart Contracts
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            Contract Information
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-violet-100
            flex
            items-center
            justify-center
          "
        >
          <ShieldCheck
            size={26}
            className="text-[#6D4AFF]"
          />
        </div>

      </div>

      <div className="space-y-5">

        {cards.map((card) => (

          <div
            key={card.title}
            className="
              rounded-2xl
              border
              border-gray-200
              p-5
            "
          >

            <div className="flex justify-between items-center">

              <div>

                <p className="text-sm text-gray-500">
                  {card.title}
                </p>

                <p className="mt-2 font-semibold break-all">
                  {card.address}
                </p>

              </div>

              <div className="flex gap-3">

                <button
                  onClick={() => copy(card.address)}
                  className="
                    h-10
                    w-10
                    rounded-xl
                    bg-gray-100
                    flex
                    items-center
                    justify-center
                  "
                >
                  <Copy size={18} />
                </button>

                <a
                  href={`${explorer}/address/${card.address}`}
                  target="_blank"
                  rel="noreferrer"
                  className="
                    h-10
                    w-10
                    rounded-xl
                    bg-violet-100
                    flex
                    items-center
                    justify-center
                  "
                >
                  <ExternalLink
                    size={18}
                    className="text-violet-600"
                  />
                </a>

              </div>

            </div>

          </div>

        ))}

      </div>

    </section>

  );

}