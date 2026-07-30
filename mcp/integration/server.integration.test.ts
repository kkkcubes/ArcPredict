import request from "supertest";
import { describe, it, expect, vi } from "vitest";

vi.mock("../services/healthService.js", () => ({
    isBackendHealthy: vi.fn(() => Promise.resolve(true))
}));

import { httpServer } from "../server.js";

describe("HTTP Server Integration", () => {

    it("should return health status", async () => {

        const response = await request(httpServer)
            .get("/health");

        expect(response.status).toBe(200);

        expect(response.body).toEqual({
            status: "ok",
            service: "ArcPredict MCP"
        });

    });

    it("should return readiness status", async () => {

        const response = await request(httpServer)
            .get("/ready");

        expect(response.status).toBe(200);

        expect(response.body).toEqual({
            status: "ready",
            service: "ArcPredict MCP"
        });

    });

    it("should expose MCP endpoint", async () => {

        const response = await request(httpServer)
            .post("/mcp")
            .send({});

        expect(response.status).not.toBe(404);

    });

    it("should accept an initialize request", async () => {

        const response = await request(httpServer)
            .post("/mcp")
            .send({
                jsonrpc: "2.0",
                id: 1,
                method: "initialize",
                params: {
                    protocolVersion: "2025-03-26",
                    capabilities: {},
                    clientInfo: {
                        name: "vitest",
                        version: "1.0.0"
                    }
                }
            });

        expect(response.status).not.toBe(404);
        expect(response.status).toBeLessThan(500);

    });

    it("should list available tools", async () => {

        const response = await request(httpServer)
            .post("/mcp")
            .set("Accept", "application/json, text/event-stream")
            .send({
                jsonrpc: "2.0",
                id: 2,
                method: "tools/list",
                params: {}
            });

        expect(response.status).not.toBe(404);
        expect(response.status).toBeLessThan(500);

    });

    it("should accept a tools/call request", async () => {

        const response = await request(httpServer)
            .post("/mcp")
            .set("Accept", "application/json, text/event-stream")
            .send({
                jsonrpc: "2.0",
                id: 3,
                method: "tools/call",
                params: {
                    name: "get_market_summary",
                    arguments: {}
                }
            });

        expect(response.status).not.toBe(404);
        expect(response.status).toBeLessThan(500);

    });

    it("should reject non-POST requests to /mcp", async () => {

        const response = await request(httpServer)
            .get("/mcp");

        expect(response.status).toBe(405);

        expect(response.body).toEqual({
            error: "Method Not Allowed"
        });

    });

    it("should include the X-Service header", async () => {

        const response = await request(httpServer)
            .get("/health");

        expect(response.status).toBe(200);

        expect(response.headers["x-service"])
            .toBe("ArcPredict MCP");

    });

    it("should include the X-Content-Type-Options header", async () => {

        const response = await request(httpServer)
            .get("/health");

        expect(response.status).toBe(200);

        expect(
            response.headers["x-content-type-options"]
        ).toBe("nosniff");

    });

    it("should return JSON for unknown routes", async () => {

        const response = await request(httpServer)
            .get("/unknown-route");

        expect(response.status).toBe(404);

        expect(response.body).toEqual({
            error: "Not Found"
        });

        expect(response.headers["content-type"])
            .toContain("application/json");

    });

    it("should include the Cache-Control header", async () => {

        const response = await request(httpServer)
            .get("/health");

        expect(response.status).toBe(200);

        expect(
            response.headers["cache-control"]
        ).toBe("no-store");

    });

});