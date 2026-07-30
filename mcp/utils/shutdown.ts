import { Server } from "http";

import { logger } from "./logger.js";

export function registerGracefulShutdown(
    server: Server
): void {

    const shutdown = (signal: string) => {
        logger.info(
            `${signal} received. Shutting down gracefully...`
        );

        server.close(() => {
            logger.info("HTTP server stopped.");
            process.exit(0);
        });
    };

    process.on("SIGINT", () => shutdown("SIGINT"));
    process.on("SIGTERM", () => shutdown("SIGTERM"));
}