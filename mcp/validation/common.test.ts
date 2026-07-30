import { describe, expect, it } from "vitest";
import {
    marketIdSchema,
    optionalLimitSchema,
    walletAddressSchema,
} from "./common.js";

describe("walletAddressSchema", () => {
    it("accepts a valid wallet address", () => {
        const result = walletAddressSchema.safeParse("0x123456789");

        expect(result.success).toBe(true);
    });

    it("rejects an empty string", () => {
        const result = walletAddressSchema.safeParse("");

        expect(result.success).toBe(false);
    });
});

describe("marketIdSchema", () => {
    it("accepts a positive integer", () => {
        const result = marketIdSchema.safeParse(10);

        expect(result.success).toBe(true);
    });

    it("rejects zero", () => {
        const result = marketIdSchema.safeParse(0);

        expect(result.success).toBe(false);
    });

    it("rejects negative numbers", () => {
        const result = marketIdSchema.safeParse(-5);

        expect(result.success).toBe(false);
    });

    it("rejects decimal numbers", () => {
        const result = marketIdSchema.safeParse(10.5);

        expect(result.success).toBe(false);
    });
});

describe("optionalLimitSchema", () => {
    it("accepts undefined", () => {
        const result = optionalLimitSchema.safeParse(undefined);

        expect(result.success).toBe(true);
    });

    it("accepts a valid limit", () => {
        const result = optionalLimitSchema.safeParse(50);

        expect(result.success).toBe(true);
    });

    it("rejects values greater than 100", () => {
        const result = optionalLimitSchema.safeParse(101);

        expect(result.success).toBe(false);
    });
});