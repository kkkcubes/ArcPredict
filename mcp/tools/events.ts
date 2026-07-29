import axios from "axios";


const BACKEND_URL =
    process.env.BACKEND_URL ||
    "http://localhost:8080";



export async function getRecentEvents() {


    const response =
        await axios.get(
            `${BACKEND_URL}/api/events`
        );


    return response.data;

}