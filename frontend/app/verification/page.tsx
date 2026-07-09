"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function VerificationPage() {

  const router = useRouter();

  useEffect(() => {

    router.replace("/#verification");

  }, [router]);

  return (

    <main className="container py-10">

      Redirecting...

    </main>

  );

}