"use client";

export default function ContractInfo() {

  const address =
    process.env
      .NEXT_PUBLIC_PREDICTION_MARKET || "";

  const explorer =
    process.env
      .NEXT_PUBLIC_EXPLORER || "";

  const treasury =
  process.env
    .NEXT_PUBLIC_MARKET_TREASURY || "";

const vault =
  process.env
    .NEXT_PUBLIC_USDC_VAULT || "";    

  const shortAddress =
    `${address.slice(0, 6)}...${address.slice(-4)}`;

  const copyAddress = async () => {

    await navigator.clipboard.writeText(
      address
    );

    alert("Address copied");
  };

  return (

    <div>

      <p className="text-gray-400">
        Prediction Market
      </p>

      <p className="font-medium">
        {shortAddress}
      </p>

      <div className="flex gap-3 mt-2">

        <button
          onClick={copyAddress}
          className="
            text-blue-400
            text-sm
          "
        >
          Copy
        </button>

        <a
          href={`${explorer}/address/${address}`}
          target="_blank"
          rel="noreferrer"
          className="
            text-green-400
            text-sm
          "
        >
          View Contract
        </a>

      </div>

      <div className="mt-4">

  <p className="text-gray-400">
    Treasury
  </p>

  <a
    href={`${explorer}/address/${treasury}`}
    target="_blank"
    rel="noreferrer"
    className="
      text-green-400
      text-sm
    "
  >
    View Treasury
  </a>

</div>

<div className="mt-4">

  <p className="text-gray-400">
    Vault
  </p>

  <a
    href={`${explorer}/address/${vault}`}
    target="_blank"
    rel="noreferrer"
    className="
      text-green-400
      text-sm
    "
  >
    View Vault
  </a>

</div>

    </div>

  );
}