import { httpClient } from "../services/httpClient.js";

export async function getLeaderboard() {

    try {

        console.log(
            "Calling leaderboard: /api/leaderboard"
        );

        const response =
            await httpClient.get(
                "/api/leaderboard"
            );

        console.log(
            "Leaderboard response:",
            response.data
        );

        return response.data;

    } catch (error: any) {

        console.error(
            "Leaderboard failed:",
            error.message
        );

        throw error;

    }

}