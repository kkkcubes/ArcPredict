import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getMarketDetails } from "./marketDetails.js";

describe("getMarketDetails", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should fetch market details successfully", async () => {

        const mockResponse = {
            data: {
                marketId: 1,
                question: "Will BTC reach $150k?"
            }
        };

        vi.mocked(httpClient.get).mockResolvedValue(mockResponse);

        const result = await getMarketDetails(1);

        expect(httpClient.get).toHaveBeenCalledWith("/api/markets/1");

        expect(result).toEqual(mockResponse.data);
    });

    it("should propagate backend errors", async () => {

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getMarketDetails(1)
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith("/api/markets/1");
    });

});