import { httpClient } from "./httpClient.js";

export async function isBackendHealthy(): Promise<boolean> {
    try {
        const response = await httpClient.get("/actuator/health");

        return response.data?.status === "UP";
    } catch {
        return false;
    }
}