const port = Number(process.env.PORT ?? 3001);

if (
    Number.isNaN(port) ||
    port <= 0 ||
    port > 65535
) {
    throw new Error("Invalid PORT environment variable");
}

const requestTimeout = Number(
    process.env.REQUEST_TIMEOUT_MS ?? 30000
);

if (
    Number.isNaN(requestTimeout) ||
    requestTimeout <= 0
) {
    throw new Error(
        "Invalid REQUEST_TIMEOUT_MS environment variable"
    );
}

export const config = {
    port,
    requestTimeout,
};