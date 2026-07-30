import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";
import { EventEmitter } from "events";
import { registerGracefulShutdown } from "./shutdown.js";

describe("registerGracefulShutdown", () => {
    const originalProcessOn = process.on;
    const listeners = new EventEmitter();

    beforeEach(() => {
        vi.spyOn(process, "on").mockImplementation(
            ((event: string, handler: (...args: unknown[]) => void) => {
                listeners.on(event, handler);
                return process;
            }) as typeof process.on
        );
    });

    afterEach(() => {
        vi.restoreAllMocks();
        listeners.removeAllListeners();
        process.on = originalProcessOn;
    });

    it("registers SIGINT and SIGTERM handlers", () => {
        const server = {
            close: vi.fn((cb?: () => void) => cb?.())
        };

        registerGracefulShutdown(server as never);

        expect(process.on).toHaveBeenCalledWith(
            "SIGINT",
            expect.any(Function)
        );

        expect(process.on).toHaveBeenCalledWith(
            "SIGTERM",
            expect.any(Function)
        );
    });
});