import { httpClient } from "../services/httpClient.js";

export async function getPortfolioSummary(
    wallet: string
) {

    const response =
        await httpClient.get(
            `/api/portfolio/${wallet}`
        );

    return response.data;

}