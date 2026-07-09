"use client";

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

        <SectionHeader
          eyebrow="Network"
          title="Verification"
          subtitle="Monitor network connectivity, deployed contracts, and blockchain health in real time."
        />

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