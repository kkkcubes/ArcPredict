import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getMarketSentiment } from "./sentiment.js";

describe("getMarketSentiment", () => {

    const consoleErrorSpy = vi.spyOn(console, "error");

    beforeEach(() => {
        vi.clearAllMocks();
    });

    afterEach(() => {
        consoleErrorSpy.mockClear();
    });

    it("should return market sentiment", async () => {

        const sentiment = {
            yesPercentage: 72,
            noPercentage: 28
        };

        vi.mocked(httpClient.get).mockResolvedValue({
            data: sentiment
        });

        const result = await getMarketSentiment();

        expect(httpClient.get).toHaveBeenCalledWith(
            "/api/markets/sentiment"
        );

        expect(consoleErrorSpy).toHaveBeenCalledWith(
            "Calling backend: /api/markets/sentiment"
        );

        expect(consoleErrorSpy).toHaveBeenCalledWith(
            "Sentiment response received"
        );

        expect(result).toEqual(sentiment);

    });

    it("should log and propagate backend errors", async () => {

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getMarketSentiment()
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith(
            "/api/markets/sentiment"
        );

        expect(consoleErrorSpy).toHaveBeenCalledWith(
            "Sentiment tool failed:",
            "Backend unavailable"
        );

    });

});