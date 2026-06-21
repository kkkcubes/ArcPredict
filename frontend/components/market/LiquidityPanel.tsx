"use client";

interface Props {
  market: any;
}

export default function LiquidityPanel({
  market,
}: Props) {

  const yesPool =
    Number(
      market?.yesPool || 0
    );

  const noPool =
    Number(
      market?.noPool || 0
    );

  const total =
    yesPool + noPool;

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-4
        "
      >
        Liquidity
      </h2>

      <div className="space-y-4">

        <div>

          <p className="text-gray-400">
            YES Liquidity
          </p>

          <p className="text-green-500 text-2xl font-bold">
            {yesPool}
          </p>

        </div>

        <div>

          <p className="text-gray-400">
            NO Liquidity
          </p>

          <p className="text-red-500 text-2xl font-bold">
            {noPool}
          </p>

        </div>

        <div>

          <p className="text-gray-400">
            Total Liquidity
          </p>

          <p className="text-3xl font-bold">
            {total}
          </p>

        </div>

      </div>

    </div>
  );
}