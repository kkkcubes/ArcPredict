"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function LeaderboardPage() {

  const router = useRouter();

  useEffect(() => {

    router.replace("/#leaderboard");

  }, [router]);

  return (

    <main className="container py-10">

      Redirecting...

    </main>

  );

}