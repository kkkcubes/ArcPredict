import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";


export async function getMarketSummary() {


    const response =
        await axios.get(
            `${BACKEND_URL}/api/markets`
        );


    return {

        totalMarkets:
            response.data.length,

        markets:
            response.data

    };

}