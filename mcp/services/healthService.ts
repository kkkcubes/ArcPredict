import { httpClient } from "./httpClient.js";

export async function isBackendHealthy(): Promise<boolean> {
    try {

        await httpClient.get("/health");

        return true;

    } catch {

        return false;

    }
}