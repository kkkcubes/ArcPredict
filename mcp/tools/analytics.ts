import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";



export async function getAnalyticsSummary() {


    const response =
        await axios.get(
            `${BACKEND_URL}/api/analytics`
        );


    return response.data;

}