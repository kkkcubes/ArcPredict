"use client";

import Link from "next/link";

import NetworkStatus from "@/components/verification/NetworkStatus";
import ContractStatus from "@/components/verification/ContractStatus";
import LatestBlock from "@/components/verification/LatestBlock";
import RPCHealth from "@/components/verification/RPCHealth";

import Card from "@/components/ui/Card";
import SectionHeader from "@/components/ui/SectionHeader";

export default function VerificationSection() {

  return (

    <section
      id="verification"
      className="mb-8"
    >

      <Card className="p-8">

        <div
          className="
            flex
            items-start
            justify-between
            gap-4
            mb-8
          "
        >

          <SectionHeader
            eyebrow="Network"
            title="Verification"
            subtitle="Monitor network connectivity, deployed contracts, and blockchain health in real time."
          />

          <Link
            href="/verification"
            className="
              whitespace-nowrap
              text-violet-600
              font-semibold
              hover:underline
            "
          >
            View More →
          </Link>

        </div>

        <div
          className="
            grid
            grid-cols-1
            lg:grid-cols-2
            gap-6
          "
        >

          <NetworkStatus />

          <ContractStatus />

          <LatestBlock />

          <RPCHealth />

        </div>

      </Card>

    </section>

  );

}