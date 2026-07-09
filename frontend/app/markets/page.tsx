"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function MarketsPage() {

  const router = useRouter();

  useEffect(() => {

    router.replace("/#markets");

  }, [router]);

  return (

    <main className="container py-10">

      Redirecting...

    </main>

  );

}