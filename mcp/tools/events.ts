import { httpClient } from "../services/httpClient.js";

export async function getRecentEvents() {

    const response =
        await httpClient.get(
            "/api/events"
        );

    return response.data;

}