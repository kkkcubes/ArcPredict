import { analyticsService }
  from "./analyticsService";

import { marketService }
  from "./marketService";

export const aiService = {

  async ask(
    prompt: string
  ) {

    const markets =
      await marketService
        .getAllMarkets() as any[];

    const analytics =
      await analyticsService
        .getAnalytics();

    const query =
      prompt.toLowerCase();

    if (
      query.includes(
        "market"
      ) &&
      query.includes(
        "volume"
      )
    ) {

      let topMarket: any =
        null;

      let volume =
        0;

      for (
        const market of markets as any[]
      ) {

        const current =
          Number(
            market.yesPool
          ) +
          Number(
            market.noPool
          );

        if (
          current >
          volume
        ) {
          volume =
            current;

          topMarket =
            market;
        }
      }

      return {
        answer:
          topMarket
            ? `Most active market: ${topMarket.question}`
            : "No markets found",
      };
    }

    if (
      query.includes(
        "sentiment"
      )
    ) {

      return {
        answer:
          `Bullish ${analytics.bullish.toFixed(
            2
          )}% | Bearish ${analytics.bearish.toFixed(
            2
          )}%`,
      };
    }

    return {
      answer:
        "No matching ArcPredict data found.",
    };
  },
};