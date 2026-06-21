"use client";

import { useState } from "react";

export default function AIPage() {

  const [query, setQuery] =
    useState("");

  return (
    <main className="container">

      <h1 className="text-4xl font-bold mb-6">
        AI Assistant
      </h1>

      <div className="card p-6">

        <textarea
          value={query}
          onChange={(e) =>
            setQuery(e.target.value)
          }
          placeholder="Which market has highest volume?"
          className="
            w-full
            h-40
            p-4
            bg-black
            border
            border-gray-800
            rounded-xl
          "
        />

        <button
          className="
            mt-4
            px-6
            py-3
            bg-blue-600
            rounded-xl
          "
        >
          Ask AI
        </button>

      </div>

    </main>
  );
}