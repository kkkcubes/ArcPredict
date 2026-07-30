import request from "supertest";
import { describe, it, expect } from "vitest";

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

        // We'll validate the response body in the next step.

    });

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