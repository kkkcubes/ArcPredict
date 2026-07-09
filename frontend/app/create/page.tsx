"use client";

import { useState } from "react";
import { toast } from "react-hot-toast";

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

        toast.success(
          "Market Created Successfully"
        );

        setQuestion("");
        setCategory("Crypto");
        setEndDate("");

      } catch (error) {

        console.error(error);

        toast.error(
          "Market Creation Failed"
        );

      }
    };

  return (
    <main className="container">

      <div className="mb-10">

  <p className="text-sm text-violet-600 font-semibold uppercase tracking-wider">
    Prediction Market
  </p>

  <h1 className="text-5xl font-bold text-gray-900 mt-2">
    Create a New Market
  </h1>

  <p className="text-lg text-gray-500 mt-4 max-w-2xl">
    Launch a new prediction market on ArcPredict. Define a clear question,
    choose a category, and set the resolution date.
  </p>

</div>

      <section
  className="
    card
    max-w-3xl
    mx-auto
    rounded-3xl
    p-10
    shadow-lg
    border
    border-gray-200
  "
>

        <div className="mb-5">

          <label className="block mb-2 font-semibold text-gray-700">
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
  bg-white
  text-gray-900
  border
  border-gray-300
  focus:outline-none
  focus:ring-2
  focus:ring-violet-500
  focus:border-violet-500
  transition
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
  bg-white
  text-gray-900
  border
  border-gray-300
  focus:outline-none
  focus:ring-2
  focus:ring-violet-500
  focus:border-violet-500
  transition
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
  bg-white
  text-gray-900
  border
  border-gray-300
  focus:outline-none
  focus:ring-2
  focus:ring-violet-500
  focus:border-violet-500
  transition
"
          />

        </div>

        <button
          onClick={handleCreateMarket}
          disabled={
  isPending ||
  !question.trim() ||
  !endDate
}
          className="
  w-full
  py-4
  rounded-2xl
  bg-violet-600
  text-white
  font-semibold
  text-lg
  hover:bg-violet-700
  transition-all
  disabled:opacity-50
  disabled:cursor-not-allowed
"
        >
          {
  isPending
    ? "Creating Market..."
    : "Create Market"
}
        </button>

      </section>

    </main>
  );
}