"use client";

import LoadingSkeleton from "@/components/ui/LoadingSkeleton";
import EmptyState from "@/components/ui/EmptyState";

interface Props {
  trades: any[];
  loading?: boolean;
}

export default function RecentTradesSection({
  trades,
  loading = false,
}: Props) {

  if (loading) {
  return (
    <LoadingSkeleton
      className="
        h-[420px]
        rounded-3xl
        bg-white
        border
        border-gray-200
      "
    />
  );
}

  return (
    <section className="mb-8">

      <div className="dashboard-card p-8">

        <h2 className="text-3xl font-bold mb-6">
          Recent Trades
        </h2>

        {trades.length === 0 ? (
         <EmptyState
  title="No Recent Trades"
  description="Trading activity will appear here after users begin placing trades."
/>
        ) : (
          <div className="overflow-x-auto">

            <table
  className="
    w-full
    min-w-[640px]
    text-left
  "
>

              <thead>

                <tr className="border-b border-gray-200">

                  <th className="py-4">
                    Market
                  </th>

                  <th className="py-4">
                    Side
                  </th>

                  <th className="py-4">
                    Amount
                  </th>

                  <th className="py-4">
                    Wallet
                  </th>

                </tr>

              </thead>

              <tbody>

                {trades.map((trade) => (

                  <tr
                    key={`${trade.txHash}-${trade.marketId}`}
                    className="
  border-b
  border-gray-100
  transition-all
  duration-300
  hover:bg-violet-50
"
                  >

                    <td className="py-4">
                      #{trade.marketId}
                    </td>

                    <td className="py-4">

                     <span
  className={`
    inline-flex
    items-center
    rounded-full
    px-3
    py-1
    text-xs
    font-bold
    ${
      trade.yesPosition
        ? "bg-green-100 text-green-700"
        : "bg-red-100 text-red-700"
    }
  `}
>
  {trade.yesPosition ? "YES" : "NO"}
</span>

                    <td
  className="
    py-4
    font-bold
    text-violet-600
  "
>
  {trade.amount}
</td>

                    <td
  className="
    py-4
    font-medium
    text-gray-900
  "
></td>
                      {trade.trader?.slice(0, 6)}
                      ...
                      {trade.trader?.slice(-4)}
                    </td>

                  </tr>

                ))}

              </tbody>

            </table>

          </div>
        )}

      </div>

    </section>
  );
}