import { httpClient } from "../services/httpClient.js";

export async function getMarketDetails(
    marketId: number
) {
    const response =
        await httpClient.get(
            `/api/markets/${marketId}`
        );

    return response.data;
}