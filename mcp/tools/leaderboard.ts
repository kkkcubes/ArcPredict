import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";


export async function getLeaderboard() {

    try {

        console.log(
            "Calling leaderboard:",
            `${BACKEND_URL}/api/leaderboard`
        );


        const response =
            await axios.get(
                `${BACKEND_URL}/api/leaderboard`
            );


        console.log(
            "Leaderboard response:",
            response.data
        );


        return response.data;


    } catch(error:any) {


        console.error(
            "Leaderboard failed:",
            error.message
        );


        throw error;

    }

}