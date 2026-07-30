import { httpClient } from "../services/httpClient.js";

export async function getAnalyticsSummary() {

    const response =
        await httpClient.get(
            "/api/analytics"
        );

    return response.data;

}