"use client";

import { useAccount } from "wagmi";

export default function TopBar() {
  const {
    address,
    isConnected,
  } = useAccount();

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

        <div>
          {isConnected ? (
            <span
              className="
                text-sm
                text-gray-300
              "
            >
              {address?.slice(
                0,
                6
              )}
              ...
              {address?.slice(
                -4
              )}
            </span>
          ) : (
            <span
              className="
                text-red-400
              "
            >
              Wallet Not Connected
            </span>
          )}
        </div>
      </div>
    </div>
  );
}