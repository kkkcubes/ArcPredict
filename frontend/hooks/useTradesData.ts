"use client";

import {
  useTradesContext,
} from "@/providers/TradesProvider";

export function useTradesData() {

  return useTradesContext();

}