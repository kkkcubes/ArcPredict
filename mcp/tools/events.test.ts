import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getRecentEvents } from "./events.js";

describe("getRecentEvents", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should return recent events", async () => {

        const events = [
            {
                id: 1,
                type: "MARKET_CREATED"
            },
            {
                id: 2,
                type: "TRADE_EXECUTED"
            }
        ];

        vi.mocked(httpClient.get).mockResolvedValue({
            data: events
        });

        const result = await getRecentEvents();

        expect(httpClient.get).toHaveBeenCalledWith(
            "/api/events"
        );

        expect(result).toEqual(events);

    });

    it("should propagate backend errors", async () => {

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getRecentEvents()
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith(
            "/api/events"
        );

    });

});