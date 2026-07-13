"use client";

import {
  usePortfolioContext,
} from "@/providers/PortfolioProvider";

export function usePortfolio() {

  return usePortfolioContext();

}