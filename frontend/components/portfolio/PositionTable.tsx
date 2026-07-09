"use client";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";
import LoadingSkeleton from "@/components/ui/LoadingSkeleton";

export default function PositionTable() {

  const {
  portfolio,
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
        Position Summary
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

              <th
                className="
                  text-left
                  py-4
                  px-2
                  text-sm
                  font-semibold
                  text-gray-500
                "
              >
                Metric
              </th>

              <th
                className="
                  text-left
                  py-4
                  px-2
                  text-sm
                  font-semibold
                  text-gray-500
                "
              >
                Value
              </th>

            </tr>

          </thead>

          <tbody>

            <tr
              className="
                border-b
                border-gray-100
              "
            >

              <td className="py-4 px-2">
                YES Positions
              </td>

              <td
                className="
                  py-4
                  px-2
                  font-semibold
                "
              >
                {portfolio?.yesPositions ?? 0}
              </td>

            </tr>

            <tr
              className="
                border-b
                border-gray-100
              "
            >

              <td className="py-4 px-2">
                NO Positions
              </td>

              <td
                className="
                  py-4
                  px-2
                  font-semibold
                "
              >
                {portfolio?.noPositions ?? 0}
              </td>

            </tr>

            <tr>

              <td className="py-4 px-2">
                Total Trades
              </td>

              <td
                className="
                  py-4
                  px-2
                  font-semibold
                "
              >
                {portfolio?.totalTrades ?? 0}
              </td>

            </tr>

          </tbody>

        </table>

      </div>

    </div>

  );

}