import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getPortfolioSummary } from "./portfolio.js";

describe("getPortfolioSummary", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should return portfolio summary", async () => {

        const wallet = "0x123456789";

        const portfolio = {
            totalValue: 5000,
            totalPositions: 3,
            holdings: [
                {
                    marketId: 1,
                    shares: 100
                }
            ]
        };

        vi.mocked(httpClient.get).mockResolvedValue({
            data: portfolio
        });

        const result = await getPortfolioSummary(wallet);

        expect(httpClient.get).toHaveBeenCalledWith(
            `/api/portfolio/${wallet}`
        );

        expect(result).toEqual(portfolio);

    });

    it("should propagate backend errors", async () => {

        const wallet = "0x123456789";

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getPortfolioSummary(wallet)
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith(
            `/api/portfolio/${wallet}`
        );

    });

});