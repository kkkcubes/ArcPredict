"use client";

import { useAccount } from "wagmi";
import { useArcBalance } from "@/hooks/useArcBalance";

export default function TopBar() {
  const {
    address,
    isConnected,
  } = useAccount();

  const {
  vaultBalance,
  liquidity,
  refresh,
  loading,
} = useArcBalance();

  return (
    <div
      className="
        border-b
        border-gray-800
        bg-gray-950
      "
    >
      <div
        className="
          container
          flex
          justify-between
          items-center
          py-3
        "
      >
        <div>
          <span
            className="
              text-green-500
            "
          >
            ●
          </span>

          <span
            className="
              ml-2
              text-gray-300
            "
          >
            Arc Testnet
          </span>
        </div>

        <div
  className="
    flex
    items-center
    gap-6
  "
>

  {isConnected ? (

    <>

      <div className="text-right">

        <p
          className="
            text-xs
            uppercase
            tracking-wide
            text-gray-500
          "
        >
          Wallet
        </p>

        <p
          className="
            text-sm
            font-semibold
            text-white
          "
        >
          {address?.slice(0, 6)}
          ...
          {address?.slice(-4)}
        </p>

      </div>

      <div className="text-right">

        <p
          className="
            text-xs
            uppercase
            tracking-wide
            text-gray-500
          "
        >
          Status
        </p>

        <p
          className="
            text-sm
            font-semibold
            text-green-400
          "
        >
          Connected
        </p>

      </div>

      <div className="text-right">

  <p
    className="
      text-xs
      uppercase
      tracking-wide
      text-gray-500
    "
  >
    Vault
  </p>

  <p
    className="
      text-sm
      font-semibold
      text-white
    "
  >
    {loading
      ? "Loading..."
      : `${vaultBalance} USDC`}
  </p>

</div>

<div className="text-right">

  <p
    className="
      text-xs
      uppercase
      tracking-wide
      text-gray-500
    "
  >
    Liquidity
  </p>

  <p
    className="
      text-sm
      font-semibold
      text-white
    "
  >
    {loading
      ? "Loading..."
      : `${liquidity} USDC`}
  </p>

</div>

<button
  onClick={refresh}
  className="
    rounded-xl
    bg-violet-600
    hover:bg-violet-700
    px-4
    py-2
    text-sm
    font-semibold
    text-white
    transition
  "
>
  Refresh
</button>

    </>

  ) : (

    <div className="text-right">

      <p
        className="
          text-xs
          uppercase
          tracking-wide
          text-gray-500
        "
      >
        Status
      </p>

      <p
        className="
          text-sm
          font-semibold
          text-red-400
        "
      >
        Wallet Not Connected
      </p>

    </div>

  )}

</div>
      </div>
    </div>
  );
}