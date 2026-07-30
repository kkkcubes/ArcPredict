import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getAnalyticsSummary } from "./analytics.js";

describe("getAnalyticsSummary", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should return analytics data", async () => {

        const analytics = {
            totalVolume: 125000,
            totalTrades: 420,
            activeMarkets: 18
        };

        vi.mocked(httpClient.get).mockResolvedValue({
            data: analytics
        });

        const result = await getAnalyticsSummary();

        expect(httpClient.get).toHaveBeenCalledWith("/api/analytics");

        expect(result).toEqual(analytics);

    });

    it("should propagate backend errors", async () => {

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getAnalyticsSummary()
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith("/api/analytics");

    });

});