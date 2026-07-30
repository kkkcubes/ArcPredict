import { httpClient } from "../services/httpClient.js";

export async function getMarketSentiment() {

    try {

        console.error(
            "Calling backend: /api/markets/sentiment"
        );

        const response =
            await httpClient.get(
                "/api/markets/sentiment"
            );

        console.error(
            "Sentiment response received"
        );

        return response.data;

    } catch (error: any) {

        console.error(
            "Sentiment tool failed:",
            error.message
        );

        throw error;

    }

}