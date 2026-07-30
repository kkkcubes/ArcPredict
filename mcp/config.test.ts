import { describe, it, expect, vi } from "vitest";

describe("config", () => {
    it("uses default port when PORT is not set", async () => {
        vi.stubEnv("PORT", undefined);

        vi.resetModules();
        const { config } = await import("./config.js");

        expect(config.port).toBe(3001);

        vi.unstubAllEnvs();
    });

    it("uses PORT environment variable", async () => {
        vi.stubEnv("PORT", "8080");

        vi.resetModules();
        const { config } = await import("./config.js");

        expect(config.port).toBe(8080);

        vi.unstubAllEnvs();
    });

    it("throws for invalid PORT", async () => {
        vi.stubEnv("PORT", "abc");

        vi.resetModules();

        await expect(import("./config.js"))
            .rejects
            .toThrow("Invalid PORT environment variable");

        vi.unstubAllEnvs();
    });
});

it("should use REQUEST_TIMEOUT_MS from environment", async () => {
    process.env.REQUEST_TIMEOUT_MS = "45000";

    vi.resetModules();

    const { config } = await import("./config.js");

    expect(config.requestTimeout).toBe(45000);

    delete process.env.REQUEST_TIMEOUT_MS;
});