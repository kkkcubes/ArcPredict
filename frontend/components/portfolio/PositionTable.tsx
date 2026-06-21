"use client";

import { usePortfolio } from "@/hooks/usePortfolio";

export default function PositionTable() {

  const {
    portfolio,
    loading,
  } = usePortfolio();

  if (loading) {
    return (
      <div>
        Loading Positions...
      </div>
    );
  }

  return (
    <div className="card p-6">

      <h2
        className="
          text-2xl
          font-bold
          mb-6
        "
      >
        Position Summary
      </h2>

      <table
        className="
          w-full
        "
      >

        <thead>

          <tr
            className="
              border-b
              border-gray-800
            "
          >
            <th className="text-left py-3">
              Metric
            </th>

            <th className="text-left py-3">
              Value
            </th>
          </tr>

        </thead>

        <tbody>

          <tr>
            <td className="py-3">
              YES Positions
            </td>

            <td className="py-3">
              {portfolio?.yesPositions ?? 0}
            </td>
          </tr>

          <tr>
            <td className="py-3">
              NO Positions
            </td>

            <td className="py-3">
              {portfolio?.noPositions ?? 0}
            </td>
          </tr>

          <tr>
            <td className="py-3">
              Total Trades
            </td>

            <td className="py-3">
              {portfolio?.totalTrades ?? 0}
            </td>
          </tr>

        </tbody>

      </table>

    </div>
  );
}