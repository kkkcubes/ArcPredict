import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";



export async function getMarketSentiment() {


    try {


        console.error(
            "Calling backend:",
            `${BACKEND_URL}/api/markets/sentiment`
        );


        const response =
            await axios.get(
                `${BACKEND_URL}/api/markets/sentiment`
            );


        console.error(
            "Sentiment response received"
        );


        return response.data;


    } catch(error:any) {


        console.error(
            "Sentiment tool failed:",
            error.message
        );


        throw error;

    }

}