"use client";

import { useState } from "react";

import {
  useCreateMarket,
} from "@/hooks/useCreateMarket";

export default function CreateMarketPage() {

  const [question, setQuestion] =
    useState("");

  const [category, setCategory] =
    useState("Crypto");

  const [endDate, setEndDate] =
    useState("");

  const {
    createMarket,
    isPending,
  } = useCreateMarket();

  const handleCreateMarket =
    async () => {

      try {

        const endTime =
          Math.floor(
            new Date(endDate)
              .getTime() / 1000
          );

        await createMarket(
          question,
          category,
          endTime
        );

        alert(
          "Market Created Successfully"
        );

        setQuestion("");
        setCategory("Crypto");
        setEndDate("");

      } catch (error) {

        console.error(error);

        alert(
          "Market Creation Failed"
        );

      }
    };

  return (
    <main className="container">

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          Create Market
        </h1>

        <p className="text-gray-400 mt-2">
          Launch a new prediction market on Arc.
        </p>

      </div>

      <section className="card p-6 max-w-3xl">

        <div className="mb-5">

          <label className="block mb-2">
            Market Question
          </label>

          <input
            type="text"
            value={question}
            onChange={(e) =>
              setQuestion(
                e.target.value
              )
            }
            placeholder="Will BTC reach $150k before Dec 2026?"
            className="
              w-full
              p-3
              rounded-xl
              bg-black
              border
              border-gray-800
            "
          />

        </div>

        <div className="mb-5">

          <label className="block mb-2">
            Category
          </label>

          <select
            value={category}
            onChange={(e) =>
              setCategory(
                e.target.value
              )
            }
            className="
              w-full
              p-3
              rounded-xl
              bg-black
              border
              border-gray-800
            "
          >
            <option>
              Crypto
            </option>

            <option>
              DeFi
            </option>

            <option>
              Arc Ecosystem
            </option>

            <option>
              AI
            </option>

          </select>

        </div>

        <div className="mb-6">

          <label className="block mb-2">
            Resolution Date
          </label>

          <input
            type="datetime-local"
            value={endDate}
            onChange={(e) =>
              setEndDate(
                e.target.value
              )
            }
            className="
              w-full
              p-3
              rounded-xl
              bg-black
              border
              border-gray-800
            "
          />

        </div>

        <button
          onClick={handleCreateMarket}
          disabled={isPending}
          className="
            px-6
            py-3
            bg-blue-600
            rounded-xl
            hover:bg-blue-700
            disabled:opacity-50
          "
        >
          {
            isPending
              ? "Creating..."
              : "Create Market"
          }
        </button>

      </section>

    </main>
  );
}