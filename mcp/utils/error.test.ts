import { describe, expect, it } from "vitest";
import { formatMcpError } from "./error.js";

describe("formatMcpError", () => {
    it("formats a standard Error object", () => {
        const error = new Error("Something went wrong");

        const result = formatMcpError(error);

        expect(result).toEqual({
            content: [
                {
                    type: "text",
                    text: JSON.stringify({
                        success: false,
                        error: "Something went wrong"
                    })
                }
            ]
        });
    });

    it("formats an unknown error", () => {
        const result = formatMcpError("Unexpected");

        expect(result).toEqual({
            content: [
                {
                    type: "text",
                    text: JSON.stringify({
                        success: false,
                        error: "Unknown MCP error"
                    })
                }
            ]
        });
    });

    it("formats a null error", () => {
        const result = formatMcpError(null);

        expect(result).toEqual({
            content: [
                {
                    type: "text",
                    text: JSON.stringify({
                        success: false,
                        error: "Unknown MCP error"
                    })
                }
            ]
        });
    });
});