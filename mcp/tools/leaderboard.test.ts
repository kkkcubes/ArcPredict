import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";

vi.mock("../services/httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "../services/httpClient.js";
import { getLeaderboard } from "./leaderboard.js";

describe("getLeaderboard", () => {

    const consoleLogSpy = vi.spyOn(console, "log");
    const consoleErrorSpy = vi.spyOn(console, "error");

    beforeEach(() => {
        vi.clearAllMocks();
    });

    afterEach(() => {
        consoleLogSpy.mockClear();
        consoleErrorSpy.mockClear();
    });

    it("should return leaderboard data", async () => {

        const leaderboard = [
            {
                wallet: "0x123",
                profit: 2500
            },
            {
                wallet: "0x456",
                profit: 1800
            }
        ];

        vi.mocked(httpClient.get).mockResolvedValue({
            data: leaderboard
        });

        const result = await getLeaderboard();

        expect(httpClient.get).toHaveBeenCalledWith(
            "/api/leaderboard"
        );

        expect(consoleLogSpy).toHaveBeenCalledWith(
            "Calling leaderboard: /api/leaderboard"
        );

        expect(consoleLogSpy).toHaveBeenCalledWith(
            "Leaderboard response:",
            leaderboard
        );

        expect(result).toEqual(leaderboard);

    });

    it("should log and propagate backend errors", async () => {

        const error = new Error("Backend unavailable");

        vi.mocked(httpClient.get).mockRejectedValue(error);

        await expect(
            getLeaderboard()
        ).rejects.toThrow("Backend unavailable");

        expect(httpClient.get).toHaveBeenCalledWith(
            "/api/leaderboard"
        );

        expect(consoleErrorSpy).toHaveBeenCalledWith(
            "Leaderboard failed:",
            "Backend unavailable"
        );

    });

});