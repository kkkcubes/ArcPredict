"use client";

interface Props {
  market: any;
}

export default function MarketDetail({
  market,
}: Props) {

  if (!market) {
    return null;
  }

  return (
    <div className="card p-6">

      <h1
        className="
          text-3xl
          font-bold
          mb-4
        "
      >
        {market.question}
      </h1>

      <div
        className="
          grid
          grid-cols-2
          gap-6
        "
      >

        <div>

          <p className="text-gray-400">
            YES Pool
          </p>

          <p className="text-3xl font-bold text-green-500">
            {market.yesPool?.toString()}
          </p>

        </div>

        <div>

          <p className="text-gray-400">
            NO Pool
          </p>

          <p className="text-3xl font-bold text-red-500">
            {market.noPool?.toString()}
          </p>

        </div>

        <div>

          <p className="text-gray-400">
            End Time
          </p>

          <p>
            {
              new Date(
                Number(
                  market.endTime
                ) * 1000
              ).toLocaleString()
            }
          </p>

        </div>

        <div>

          <p className="text-gray-400">
            Resolved
          </p>

          <p>
            {market.resolved
              ? "Yes"
              : "No"}
          </p>

        </div>

      </div>

    </div>
  );
}