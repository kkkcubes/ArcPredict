import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getMarketSummary } from "./market.js";

describe("getMarketSummary", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should return market summary", async () => {

        const markets = [
            {
                marketId: 1,
                question: "Will BTC reach $150k?"
            },
            {
                marketId: 2,
                question: "Will ETH reach $10k?"
            }
        ];

        vi.mocked(httpClient.get).mockResolvedValue({
            data: markets
        });

        const result = await getMarketSummary();

        expect(httpClient.get).toHaveBeenCalledWith("/api/markets");

        expect(result).toEqual({
            totalMarkets: 2,
            markets
        });

    });

    it("should propagate backend errors", async () => {

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getMarketSummary()
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith("/api/markets");

    });

});