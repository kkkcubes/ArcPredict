import { httpClient } from "../services/httpClient.js";

export async function getMarketSummary() {

    const response =
        await httpClient.get(
            "/api/markets"
        );

    return {

        totalMarkets:
            response.data.length,

        markets:
            response.data

    };

}