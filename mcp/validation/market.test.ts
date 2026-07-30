import { describe, expect, it } from "vitest";
import { marketDetailsSchema } from "./market.js";

describe("marketDetailsSchema", () => {
    it("accepts a valid market ID", () => {
        const result = marketDetailsSchema.safeParse({
            marketId: 10
        });

        expect(result.success).toBe(true);
    });

    it("rejects a missing market ID", () => {
        const result = marketDetailsSchema.safeParse({});

        expect(result.success).toBe(false);
    });

    it("rejects zero", () => {
        const result = marketDetailsSchema.safeParse({
            marketId: 0
        });

        expect(result.success).toBe(false);
    });

    it("rejects negative market IDs", () => {
        const result = marketDetailsSchema.safeParse({
            marketId: -1
        });

        expect(result.success).toBe(false);
    });

    it("rejects decimal market IDs", () => {
        const result = marketDetailsSchema.safeParse({
            marketId: 10.5
        });

        expect(result.success).toBe(false);
    });
});