"use client";

import { useRouter } from "next/navigation";
import { ArrowLeft } from "lucide-react";

export default function BackButton() {

  const router = useRouter();

  return (

    <button
      onClick={() => router.push("/dashboard")}
      className="
        flex
        items-center
        gap-2
        mb-6
        text-violet-600
        font-medium
        hover:text-violet-700
        transition-colors
      "
    >

      <ArrowLeft size={20} />

      <span>
        Back to Dashboard
      </span>

    </button>

  );

}