import { describe, expect, it } from "vitest";
import { formatMcpSuccess } from "./success.js";

describe("formatMcpSuccess", () => {
    it("formats a simple object", () => {
        const data = {
            id: 1,
            name: "Bitcoin"
        };

        const result = formatMcpSuccess(data);

        expect(result).toEqual({
            content: [
                {
                    type: "text",
                    text: JSON.stringify(data, null, 2)
                }
            ]
        });
    });

    it("formats an array", () => {
        const data = [1, 2, 3];

        const result = formatMcpSuccess(data);

        expect(result.content[0].text).toBe(
            JSON.stringify(data, null, 2)
        );
    });

    it("formats a string", () => {
        const data = "Hello MCP";

        const result = formatMcpSuccess(data);

        expect(result.content[0].text).toBe(
            JSON.stringify(data, null, 2)
        );
    });
});