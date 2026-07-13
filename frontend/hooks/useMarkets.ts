"use client";

import {
  useMarketsContext,
} from "@/providers/MarketsProvider";

export function useMarkets() {

  return useMarketsContext();

}