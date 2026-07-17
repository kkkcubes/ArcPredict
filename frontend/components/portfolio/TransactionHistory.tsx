"use client";

import {
  useState,
} from "react";

import { useAccount } from "wagmi";

import {
  usePortfolioTransactions,
} from "@/hooks/usePortfolioTransactions";

export default function TransactionHistory() {

  const {
    address,
  } = useAccount();

  const [
  page,
  setPage,
] = useState(0);

const [
  filter,
  setFilter,
] = useState<
  "ALL" | "YES" | "NO"
>(
  "ALL"
);

 const {
  transactions,
  loading,
  error,
  totalPages,
  totalElements,
} = usePortfolioTransactions(
  address ?? "",
  page,
  20
);

const filteredTransactions =
  transactions.filter(
    (transaction) => {

      if (
        filter === "ALL"
      ) {
        return true;
      }

      if (
        filter === "YES"
      ) {
        return transaction.yesPosition;
      }

      return !transaction.yesPosition;

    }
  );

  if (loading) {

    return (

      <div className="card p-6">

        Loading transaction history...

      </div>

    );

  }

  if (error) {

    return (

      <div className="card p-6 text-red-500">

        {error}

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
        Transaction History
      </h2>

      <div
  className="
    mb-6
    flex
    gap-2
  "
>

  <button
    onClick={() =>
      setFilter("ALL")
    }
    className={`
      rounded-lg
      px-4
      py-2
      ${
        filter === "ALL"
          ? "bg-blue-600"
          : "border border-gray-700"
      }
    `}
  >
    All
  </button>

  <button
    onClick={() =>
      setFilter("YES")
    }
    className={`
      rounded-lg
      px-4
      py-2
      ${
        filter === "YES"
          ? "bg-green-600"
          : "border border-gray-700"
      }
    `}
  >
    YES
  </button>

  <button
    onClick={() =>
      setFilter("NO")
    }
    className={`
      rounded-lg
      px-4
      py-2
      ${
        filter === "NO"
          ? "bg-red-600"
          : "border border-gray-700"
      }
    `}
  >
    NO
  </button>

</div>

      <div className="overflow-x-auto">

        <table
          className="
            w-full
            text-left
          "
        >

          <thead>

            <tr
              className="
                border-b
                border-gray-700
              "
            >

              <th className="py-3">
                Market
              </th>

              <th className="py-3">
                Position
              </th>

              <th className="py-3">
                Amount
              </th>

              <th className="py-3">
                Block
              </th>

              <th className="py-3">
                Transaction
              </th>

              <th className="py-3">
                Time
              </th>

            </tr>

          </thead>

          <tbody>

  {filteredTransactions.length === 0 ? (

    <tr>

      <td
        colSpan={6}
        className="
          py-8
          text-center
          text-gray-500
        "
      >
        No transactions found.
      </td>

    </tr>

  ) : (

    filteredTransactions.map(

      (transaction) => (

        <tr
          key={transaction.id}
          className="
            border-b
            border-gray-800
          "
        >

          <td className="py-3">
            #{transaction.marketId}
          </td>

          <td className="py-3">

            <span
              className={
                transaction.yesPosition
                  ? "text-green-500"
                  : "text-red-500"
              }
            >

              {transaction.yesPosition
                ? "YES"
                : "NO"}

            </span>

          </td>

          <td className="py-3">
            {transaction.amount}
          </td>

          <td className="py-3">
            {transaction.blockNumber}
          </td>

          <td className="py-3 font-mono">

  <a
    href={`https://scan.testnet.arc.network/tx/${transaction.txHash}`}
    target="_blank"
    rel="noopener noreferrer"
    className="
      text-blue-500
      hover:underline
    "
  >

    {transaction.txHash.slice(0, 10)}...

  </a>

</td>

          <td className="py-3">
            {new Date(
              transaction.timestamp
            ).toLocaleString()}
          </td>

        </tr>

      )

    )

  )}

</tbody>

               </table>

        <div className="mt-4 text-sm text-gray-400">

          Showing{" "}

          {transactions.length}

          {" "}of{" "}

          {totalElements}

          {" "}transactions

        </div>

        <div
          className="
            mt-6
            flex
            items-center
            justify-between
          "
        >

          <button
            onClick={() =>
              setPage(
                page - 1
              )
            }
            disabled={page === 0}
            className="
              rounded-lg
              border
              border-gray-700
              px-4
              py-2
              disabled:opacity-50
              disabled:cursor-not-allowed
            "
          >
            Previous
          </button>

          <span
            className="
              text-sm
              text-gray-400
            "
          >
            Page {page + 1} of {Math.max(totalPages, 1)}
          </span>

          <button
            onClick={() =>
              setPage(
                page + 1
              )
            }
            disabled={
              page >= totalPages - 1
            }
            className="
              rounded-lg
              border
              border-gray-700
              px-4
              py-2
              disabled:opacity-50
              disabled:cursor-not-allowed
            "
          >
            Next
          </button>

        </div>

      </div>

    </div>

  );

}