"use client";

import { useState } from "react";

import BackButton
  from "@/components/ui/BackButton";

export default function AIPage() {

  const [query, setQuery] =
    useState("");

  return (

    <main className="container py-10">

      <BackButton />

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          AI Insights
        </h1>

        <p className="mt-2 text-gray-500">
          Ask questions about prediction markets,
          trading activity,
          and protocol data.
        </p>

      </div>

      <div className="dashboard-card p-8">

        <textarea
          value={query}
          onChange={(e) =>
            setQuery(e.target.value)
          }
          placeholder="Which market has the highest volume?"
          className="
            w-full
            h-40
            rounded-2xl
            border
            border-gray-300
            p-4
            resize-none
            focus:outline-none
            focus:ring-2
            focus:ring-violet-500
          "
        />

        <button
          className="
            mt-6
            rounded-xl
            bg-violet-600
            px-6
            py-3
            text-white
            transition
            hover:bg-violet-700
          "
        >
          Ask AI
        </button>

      </div>

    </main>

  );

}