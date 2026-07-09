"use client";

import {
  Server,
  Link as LinkIcon,
} from "lucide-react";

import { ARC_RPC } from "@/lib/constants";

export default function RPCHealth() {

  return (

    <section className="dashboard-card p-8">

      <div className="flex items-center justify-between mb-8">

        <div>

          <p className="text-sm text-gray-500">
            Infrastructure
          </p>

          <h2 className="text-3xl font-bold text-gray-900 mt-1">
            RPC Endpoint
          </h2>

        </div>

        <div
          className="
            h-14
            w-14
            rounded-2xl
            bg-cyan-100
            flex
            items-center
            justify-center
          "
        >
          <Server
            size={26}
            className="text-cyan-600"
          />
        </div>

      </div>

      <div
        className="
          rounded-2xl
          border
          border-gray-200
          p-5
        "
      >

        <div className="flex items-center gap-3 mb-4">

          <LinkIcon
            size={18}
            className="text-gray-500"
          />

          <span className="font-semibold text-gray-900">
            Endpoint
          </span>

        </div>

        <p
          className="
            break-all
            text-sm
            text-gray-600
          "
        >
          {ARC_RPC}
        </p>

      </div>

      <div
        className="
          mt-6
          inline-flex
          items-center
          gap-2
          rounded-full
          bg-blue-100
          px-4
          py-2
          text-sm
          font-semibold
          text-blue-700
        "
      >
        Configured
      </div>

    </section>

  );

}