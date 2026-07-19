"use client";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

import {
  formatCurrency,
  formatNumber,
} from "@/lib/format";

export default function PositionTable() {

 const {
  positions,
  loading,
} = usePortfolioContext();

  if (loading) {
    return (
      <LoadingSkeleton
        className="
          h-[260px]
          rounded-3xl
          bg-white
          border
          border-gray-200
        "
      />
    );
  }

  return (

    <div
      className="
        dashboard-card
        p-6
      "
    >

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Live Positions
      </h2>

      <div
        className="
          overflow-x-auto
        "
      >

        <table
          className="
            w-full
            min-w-[520px]
          "
        >

          <thead>

<tr
  className="
    border-b
    border-gray-200
  "
>

  <th className="py-3 text-left">
    Market
  </th>

  <th className="py-3 text-left">
    Position
  </th>

  <th className="py-3 text-left">
    Shares
  </th>

  <th className="py-3 text-left">
    Invested
  </th>

  <th className="py-3 text-left">
    Current Value
  </th>

  <th className="py-3 text-left">
    Rewards
  </th>

  <th className="py-3 text-left">
    Status
  </th>

</tr>

</thead>

          <tbody>

  {positions.length === 0 ? (

    <tr>

      <td
        colSpan={7}
        className="
          py-8
          text-center
          text-gray-500
        "
      >
        No positions found.
      </td>

    </tr>

  ) : (

    positions.map(

      (position) => (

        <tr
          key={`${position.marketId}-${position.yesPosition}`}
          className="
            border-b
            border-gray-100
          "
        >

          <td className="py-4">

            #{position.marketId}

          </td>

          <td className="py-4">

            <span
              className={
                position.yesPosition
                  ? "text-green-600 font-semibold"
                  : "text-red-600 font-semibold"
              }
            >

              {position.yesPosition
                ? "YES"
                : "NO"}

            </span>

          </td>

          <td className="py-4">

            {formatNumber(position.shares)}

          </td>

          <td className="py-4">

            {formatCurrency(position.investedAmount, "")}

          </td>

          <td
  className="
    py-4
    font-semibold
    text-blue-600
  "
>

  {formatCurrency(position.currentValue, "")}

</td>

         <td
  className="
    py-4
    font-semibold
    text-emerald-600
  "
>

  {formatCurrency(position.claimableRewards, "")}

</td>

          <td className="py-4">

            {position.claimed ? (

              <span
                className="
                  rounded-full
                  bg-gray-200
                  px-3
                  py-1
                  text-xs
                  font-semibold
                  text-gray-700
                "
              >
                Claimed
              </span>

            ) : position.winner ? (

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
                Winner
              </span>

            ) : (

  <span
    className="
      rounded-full
      bg-yellow-100
      px-3
      py-1
      text-xs
      font-semibold
      text-yellow-700
    "
  >
    Open
  </span>

)}

          </td>

        </tr>

      )

    )

  )}

</tbody>

        </table>

      </div>

    </div>

  );

}