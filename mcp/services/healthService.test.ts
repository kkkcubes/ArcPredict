import { describe, it, expect, vi, beforeEach } from "vitest";

vi.mock("./httpClient.js", () => ({
    httpClient: {
        get: vi.fn()
    }
}));

import { httpClient } from "./httpClient.js";
import { isBackendHealthy } from "./healthService.js";

describe("isBackendHealthy", () => {

    beforeEach(() => {
        vi.clearAllMocks();
    });

    it("should return true when backend is healthy", async () => {

        vi.mocked(httpClient.get).mockResolvedValue({});

        await expect(
            isBackendHealthy()
        ).resolves.toBe(true);

    });

    it("should return false when backend is unavailable", async () => {

        vi.mocked(httpClient.get).mockRejectedValue(
            new Error("Backend unavailable")
        );

        await expect(
            isBackendHealthy()
        ).resolves.toBe(false);

    });

});