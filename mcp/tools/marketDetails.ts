import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";



export async function getMarketDetails(
    marketId:number
) {


    const response =
        await axios.get(
            `${BACKEND_URL}/api/markets/${marketId}`
        );


    return response.data;

}