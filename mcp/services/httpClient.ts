import axios from "axios";

const BACKEND_URL =
    process.env.BACKEND_URL ??
    "http://localhost:8080";

export const httpClient = axios.create({
    baseURL: BACKEND_URL,
    timeout: 10000,
    headers: {
        "Content-Type": "application/json"
    }
});