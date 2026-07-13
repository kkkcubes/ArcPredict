"use client";

import BackButton from "@/components/ui/BackButton";

import VerificationSection
  from "@/components/dashboard/VerificationSection";

import SystemMetrics
  from "@/components/dashboard/SystemMetrics";

export default function VerificationPage() {

  return (

    <main className="container py-10">

      <BackButton />

      <VerificationSection />

      <div className="mt-8">

        <SystemMetrics />

      </div>

    </main>

  );

}