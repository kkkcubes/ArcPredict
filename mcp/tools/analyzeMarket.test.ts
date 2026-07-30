import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("./market.js", () => ({
    getMarketSummary: vi.fn()
}));

vi.mock("./sentiment.js", () => ({
    getMarketSentiment: vi.fn()
}));

vi.mock("./analytics.js", () => ({
    getAnalyticsSummary: vi.fn()
}));

vi.mock("./leaderboard.js", () => ({
    getLeaderboard: vi.fn()
}));

vi.mock("./events.js", () => ({
    getRecentEvents: vi.fn()
}));

import { analyzeMarket } from "./analyzeMarket.js";

import { getMarketSummary } from "./market.js";
import { getMarketSentiment } from "./sentiment.js";
import { getAnalyticsSummary } from "./analytics.js";
import { getLeaderboard } from "./leaderboard.js";
import { getRecentEvents } from "./events.js";

describe("analyzeMarket", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should combine all market data", async () => {

        vi.mocked(getMarketSummary).mockResolvedValue({
    totalMarkets: 10,
    markets: []
});

        vi.mocked(getMarketSentiment).mockResolvedValue({
            bullish: 7,
            bearish: 3
        });

        vi.mocked(getAnalyticsSummary).mockResolvedValue({
            volume: 50000
        });

        vi.mocked(getLeaderboard).mockResolvedValue([
            {
                wallet: "0x123"
            }
        ]);

        vi.mocked(getRecentEvents).mockResolvedValue([
            {
                id: 1
            }
        ]);

        const result = await analyzeMarket();

        expect(result).toEqual({

            marketSummary: {
    totalMarkets: 10,
    markets: []
},

            sentiment: {
                bullish: 7,
                bearish: 3
            },

            analytics: {
                volume: 50000
            },

            leaderboard: [
                {
                    wallet: "0x123"
                }
            ],

            recentEvents: [
                {
                    id: 1
                }
            ]

        });

    });

    it("should propagate errors from dependent tools", async () => {

        vi.mocked(getMarketSummary)
            .mockRejectedValue(
                new Error("Backend unavailable")
            );

        await expect(
            analyzeMarket()
        ).rejects.toThrow(
            "Backend unavailable"
        );

    });

});