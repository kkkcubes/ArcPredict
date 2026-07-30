export const SERVICE_NAME = "ArcPredict MCP";

export const ROUTES = {
    MCP: "/mcp",
    HEALTH: "/health",
    READY: "/ready"
} as const;

export const HEADERS = {
    CONTENT_TYPE: "Content-Type",
    CACHE_CONTROL: "Cache-Control",
    X_SERVICE: "X-Service",
    X_CONTENT_TYPE_OPTIONS: "X-Content-Type-Options"
} as const;