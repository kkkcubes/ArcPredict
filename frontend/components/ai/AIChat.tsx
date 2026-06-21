"use client";

import { useState } from "react";
import { aiService } from "@/services/aiService";
import AIResponse from "./AIResponse";

export default function AIChat() {

  const [question, setQuestion] =
    useState("");

  const [response, setResponse] =
    useState("");

  const [loading, setLoading] =
    useState(false);

  const askAI = async () => {

    if (!question) return;

    try {

      setLoading(true);

      const result =
        await aiService.ask(
          question
        );

      setResponse(
        result.answer
      );

    } finally {

      setLoading(false);
    }
  };

  return (
    <div className="card p-6">

      <h2 className="text-2xl font-bold mb-4">
        ArcPredict AI
      </h2>

      <textarea
        value={question}
        onChange={(e) =>
          setQuestion(
            e.target.value
          )
        }
        className="
          w-full
          h-32
          bg-black
          border
          border-gray-800
          rounded-xl
          p-4
        "
        placeholder="Which market has highest volume?"
      />

      <button
        onClick={askAI}
        className="
          mt-4
          bg-blue-600
          px-5
          py-3
          rounded-xl
        "
      >
        {loading
          ? "Thinking..."
          : "Ask AI"}
      </button>

      <AIResponse
        response={response}
      />

    </div>
  );
}