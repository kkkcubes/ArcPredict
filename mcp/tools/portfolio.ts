import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";



export async function getPortfolioSummary(
    wallet: string
) {


    const response =
        await axios.get(
            `${BACKEND_URL}/api/portfolio/${wallet}`
        );


    return response.data;

}