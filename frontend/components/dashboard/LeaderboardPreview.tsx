"use client";

export default function LeaderboardPreview() {

  const traders = [

    {
      rank: 1,
      address:
        "0x1234...abcd",
      volume:
        25000,
    },

    {
      rank: 2,
      address:
        "0x5678...efgh",
      volume:
        18000,
    },

    {
      rank: 3,
      address:
        "0x9999...zzzz",
      volume:
        12000,
    },

  ];

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        Top Traders
      </h2>

      <div className="space-y-3">

        {traders.map(
          (trader) => (

            <div
              key={trader.rank}
              className="
                flex
                justify-between
              "
            >
              <span>
                #{trader.rank}
              </span>

              <span>
                {trader.address}
              </span>

              <span>
                {trader.volume}
              </span>

            </div>
          )
        )}

      </div>

    </div>
  );
}